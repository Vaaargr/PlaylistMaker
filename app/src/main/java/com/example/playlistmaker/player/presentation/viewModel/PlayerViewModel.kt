package com.example.playlistmaker.player.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.musicLibrary.domain.api.interactors.PlaylistLibraryInteractor
import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView
import com.example.playlistmaker.musicLibrary.presentation.mapper.PlaylistForViewMapper
import com.example.playlistmaker.player.domain.api.interactors.PlayerInteractor
import com.example.playlistmaker.player.domain.api.interactors.PlayerPlaylistTrackInteractor
import com.example.playlistmaker.player.domain.api.interactors.PlayerTrackInteractor
import com.example.playlistmaker.player.presentation.adapter.PlaylistClickListener
import com.example.playlistmaker.player.presentation.states.PlayerState
import com.example.playlistmaker.player.presentation.states.SaveTrackInPlaylistState
import com.example.playlistmaker.search.presentation.mappers.TrackViewMapper
import com.example.playlistmaker.search.presentation.model.TrackForView
import com.example.playlistmaker.tools.SingleEventLiveData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val player: PlayerInteractor,
    private val playerTrackInteractor: PlayerTrackInteractor,
    private val playerPlaylistTrackInteractor: PlayerPlaylistTrackInteractor,
    private val playlistLibraryInteractor: PlaylistLibraryInteractor,
    private val trackViewMapper: TrackViewMapper,
    private val playlistForViewMapper: PlaylistForViewMapper,
) : ViewModel(), PlaylistClickListener {
    private var isLikeClickAllowed = true
    private var timerJob: Job? = null

    private val trackLiveData =
        MutableLiveData<TrackForView>()

    private val timerLiveData = MutableLiveData<Int>()

    private val playerStateLiveData: MutableLiveData<PlayerState> =
        MutableLiveData(PlayerState.DEFAULT())


    private val playlistStateLiveData =
        MutableLiveData<List<PlaylistForView>>(emptyList())

    private val saveTrackInPlaylistLiveData = MutableLiveData<SaveTrackInPlaylistState>()

    private fun setTrack(track: TrackForView) {
        trackLiveData.value = track
    }

    fun loadTrack(trackID: Long) {
        viewModelScope.launch {
            val track = playerTrackInteractor.loadTrack(trackID)
            setTrack(trackViewMapper.trackToTrackForViewMap(track))
            preparePlayer(getTrack().previewUrl)
        }
    }

    private fun setTimer(timer: Int) {
        timerLiveData.postValue(timer)
    }

    private fun setState(state: PlayerState) {
        playerStateLiveData.postValue(state)
    }

    private fun setPlaylistState(playlists: List<PlaylistForView>) {
        playlistStateLiveData.postValue(playlists)
    }

    fun observeTrack(): LiveData<TrackForView> = trackLiveData

    private fun getTrack(): TrackForView {
        return trackLiveData.value!!
    }

    fun getTimer(): LiveData<Int> {
        return timerLiveData
    }

    fun getPlayerState(): LiveData<PlayerState> {
        return playerStateLiveData
    }

    private fun getState(): PlayerState {
        return getPlayerState().value!!
    }

    fun observePlaylistState(): LiveData<List<PlaylistForView>> = playlistStateLiveData

    fun observeSaveTrackInPlaylist(): LiveData<SaveTrackInPlaylistState> =
        saveTrackInPlaylistLiveData

    private fun setSaveTrackInPlaylistState(state: SaveTrackInPlaylistState) {
        saveTrackInPlaylistLiveData.postValue(state)
    }

    fun playStopButtonClick() {
        when (getState()) {
            is PlayerState.DEFAULT -> preparePlayer(getTrack().previewUrl)
            is PlayerState.PAUSED -> playerPlay()
            is PlayerState.PLAYING -> playerPause()
            is PlayerState.PREPARED -> playerPlay()
        }
    }

    private fun playerPlay() {
        player.start()
        timerJob = viewModelScope.launch {
            while (true) {
                if (getState() is PlayerState.PLAYING) {
                    setTimer(player.getCurrentPosition())
                }
                delay(TIMER_DELAY_MILLIS)
            }
        }
        setState(PlayerState.PLAYING())
    }

    fun playerPause() {
        if (getState() is PlayerState.PLAYING) {
            player.pause()
            timerJob?.cancel()
            setState(PlayerState.PAUSED())
        }
    }

    private fun preparePlayer(url: String) {
        val onPreparedListener = {
            setTimer(0)
            setState(PlayerState.PREPARED())
        }
        val onCompletionListener = {
            timerJob?.cancel()
            setState(PlayerState.PREPARED())
            setTimer(0)
        }
        player.preparePlayer(url, onPreparedListener, onCompletionListener)
    }

    private fun releasePlayer() {
        timerJob?.cancel()
        player.release()
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }


    fun likeButtonClick() {
        if (isLikeClickAllowed) {
            isLikeClickAllowed = false
            val track = getTrack()
            track.isLiked = !track.isLiked
            setTrack(track)
            viewModelScope.launch {
                playerTrackInteractor.updateTrack(trackViewMapper.trackForViewToTrackMap(track))
                delay(500)
                isLikeClickAllowed = true
            }
        }
    }

    fun checkPlaylists() {
        viewModelScope.launch {
            playlistLibraryInteractor.getSavedPlaylists().collect { playlists ->
                if (playlists.isEmpty()) {
                    setPlaylistState(emptyList<PlaylistForView>())
                } else {
                    setPlaylistState(playlists.map {
                        playlistForViewMapper.playlistToPlaylistForView(
                            it
                        )
                    })
                }
            }
        }
    }

    override fun clickOnPlaylist(playlist: PlaylistForView) {
        viewModelScope.launch {
            if (playerPlaylistTrackInteractor.checkTrackInPlaylist(
                    playlist.id!!,
                    getTrack().trackId
                )
            ) {
                setSaveTrackInPlaylistState(SaveTrackInPlaylistState.Failure(playlist.name))
            } else {
                playerPlaylistTrackInteractor.addTrackToPlaylist(playlist.id!!, getTrack().trackId)
                playlist.tracksCount += 1
                playlistLibraryInteractor.updatePlaylist(
                    playlistForViewMapper.playlistForViewToPlaylistMap(
                        playlist
                    )
                )
                setSaveTrackInPlaylistState(SaveTrackInPlaylistState.Success(playlist.name))
            }
        }
    }

    fun checkAndDeleteTrack(){
        if (!getTrack().isLiked){
            viewModelScope.launch {
                if (!playerPlaylistTrackInteractor.checkTrackInAllPlaylists(getTrack().trackId)){
                    playerTrackInteractor.deleteTrack(getTrack().trackId)
                }
            }
        }
    }

    companion object {
        private const val TIMER_DELAY_MILLIS = 300L
    }
}