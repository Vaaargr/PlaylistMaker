package com.example.playlistmaker.player.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.musicLibrary.domain.api.interactors.PlaylistLibraryInteractor
import com.example.playlistmaker.musicLibrary.domain.api.interactors.SaveTrackInPlaylistUseCase
import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView
import com.example.playlistmaker.musicLibrary.presentation.mapper.PlaylistForViewMapper
import com.example.playlistmaker.player.domain.api.interactors.PlayerDatabaseInteractor
import com.example.playlistmaker.player.domain.api.interactors.PlayerInteractor
import com.example.playlistmaker.player.domain.api.interactors.ReceiveTrackUseCase
import com.example.playlistmaker.player.presentation.adapter.PlaylistClickListener
import com.example.playlistmaker.player.presentation.states.PlayerState
import com.example.playlistmaker.player.presentation.states.PlaylistsPlayerState
import com.example.playlistmaker.player.presentation.states.SaveTrackInPlaylistState
import com.example.playlistmaker.player.presentation.states.SavedTrackState
import com.example.playlistmaker.search.presentation.mappers.TrackViewMapper
import com.example.playlistmaker.search.presentation.model.TrackForView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val player: PlayerInteractor,
    private val receive: ReceiveTrackUseCase,
    private val playerDBInteractor: PlayerDatabaseInteractor,
    private val trackViewMapper: TrackViewMapper,
    private val playlistLibraryInteractor: PlaylistLibraryInteractor,
    private val playlistForViewMapper: PlaylistForViewMapper,
    private val saveTrackInPlaylist: SaveTrackInPlaylistUseCase
) : ViewModel(), PlaylistClickListener {

    private var timerJob: Job? = null

    private val trackLiveData =
        MutableLiveData<TrackForView>()

    private val timerLiveData = MutableLiveData<Int>()

    private val playerStateLiveData: MutableLiveData<PlayerState> =
        MutableLiveData(PlayerState.DEFAULT())

    private val savedTrackStateLiveData = MutableLiveData<SavedTrackState>()

    private val playlistStateLiveData =
        MutableLiveData<PlaylistsPlayerState>(PlaylistsPlayerState.Empty)

    private val saveTrackInPlaylistLiveData = MutableLiveData<SaveTrackInPlaylistState>()

    private fun setTrack(track: TrackForView) {
        trackLiveData.value = track
    }

    init {
        setTrack(trackViewMapper.trackToTrackForViewMap(receive.execute()))
        preparePlayer(getTrack().previewUrl)
        checkTrack()
    }

    private fun setTimer(timer: Int) {
        timerLiveData.postValue(timer)
    }

    private fun setState(state: PlayerState) {
        playerStateLiveData.postValue(state)
    }

    private fun setSavedTrackState(stState: SavedTrackState) {
        savedTrackStateLiveData.postValue(stState)
    }

    private fun setPlaylistState(state: PlaylistsPlayerState) {
        playlistStateLiveData.postValue(state)
    }

    fun getTrack(): TrackForView {
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

    fun getSavedTrackState(): LiveData<SavedTrackState> {
        return savedTrackStateLiveData
    }

    fun observePlaylistState(): LiveData<PlaylistsPlayerState> = playlistStateLiveData

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
        player.pause()
        timerJob?.cancel()
        setState(PlayerState.PAUSED())
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

    fun releasePlayer() {
        timerJob?.cancel()
        player.release()
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }

    private fun checkTrack() {
        viewModelScope.launch {
            if (playerDBInteractor.checkTrack(trackLiveData.value!!.trackId)) {
                setSavedTrackState(SavedTrackState.SavedTrack)
            } else {
                setSavedTrackState(SavedTrackState.UnsavedTrack)
            }
        }
    }

    fun likeButtonClick() {
        when (savedTrackStateLiveData.value!!) {
            SavedTrackState.SavedTrack -> deleteTrack()
            SavedTrackState.UnsavedTrack -> saveTrack()
        }
    }

    private fun saveTrack() {
        viewModelScope.launch {
            playerDBInteractor.saveTrack(trackViewMapper.trackForViewToTrackMap(trackLiveData.value!!))
            setSavedTrackState(SavedTrackState.SavedTrack)
        }
    }

    private fun deleteTrack() {
        viewModelScope.launch {
            playerDBInteractor.deleteTrack(trackLiveData.value!!.trackId)
            setSavedTrackState(SavedTrackState.UnsavedTrack)
        }
    }

    fun checkPlaylists() {
        viewModelScope.launch {
            playlistLibraryInteractor.getSavedPlaylists().collect { playlists ->
                if (playlists.isEmpty()) {
                    setPlaylistState(PlaylistsPlayerState.Empty)
                } else {
                    setPlaylistState(PlaylistsPlayerState.Content(playlists.map {
                        playlistForViewMapper.playlistToPlaylistForView(
                            it
                        )
                    }))
                }
            }
        }
    }

    override fun clickOnPlaylist(playlist: PlaylistForView) {
        if (playlist.tracksIDList.contains(getTrack().trackId)) {
            setSaveTrackInPlaylistState(SaveTrackInPlaylistState.Failure(playlist.name))
        } else {
            val tracks = ArrayList<Int>(playlist.tracksIDList)
            tracks.add(getTrack().trackId)
            playlist.tracksIDList = tracks
            playlist.tracksCount = playlist.tracksIDList.size
            viewModelScope.launch {
                playlistLibraryInteractor.updatePlaylist(
                    playlistForViewMapper.playlistForViewToPlaylistMap(
                        playlist
                    )
                )
                saveTrackInPlaylist.execute(trackViewMapper.trackForViewToTrackMap(getTrack()))
            }
            setSaveTrackInPlaylistState(SaveTrackInPlaylistState.Success(playlist.name))
        }
    }

    companion object {
        private const val TIMER_DELAY_MILLIS = 300L
    }
}