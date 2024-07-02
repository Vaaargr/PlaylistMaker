package com.example.playlistmaker.musicLibrary.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.musicLibrary.domain.api.interactors.PlaylistInteractor
import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView
import com.example.playlistmaker.musicLibrary.presentation.mapper.PlaylistForViewMapper
import com.example.playlistmaker.musicLibrary.presentation.states.TracksInPlaylistState
import com.example.playlistmaker.search.presentation.mappers.TrackViewMapper
import com.example.playlistmaker.search.presentation.model.TrackForView
import kotlinx.coroutines.launch

class PlaylistFragmentViewModel(
    private val interactor: PlaylistInteractor,
    private val playlistMapper: PlaylistForViewMapper,
    private val trackMapper: TrackViewMapper
) :
    ViewModel() {

    private val playlistLiveData = MutableLiveData<PlaylistForView>()

    private val tracksInPlaylistLiveData = MutableLiveData<TracksInPlaylistState>()

    fun observePlaylistLD(): LiveData<PlaylistForView> = playlistLiveData

    fun observeTracksInPlaylistLD(): LiveData<TracksInPlaylistState> = tracksInPlaylistLiveData

    private fun setPlaylist(playlist: PlaylistForView) {
        playlistLiveData.postValue(playlist)
    }

    private fun setTracksInPlaylist(tracksState: TracksInPlaylistState) {
        tracksInPlaylistLiveData.value = tracksState
    }

    fun getPlaylist(id: Long) {
        viewModelScope.launch {
            setPlaylist(playlistMapper.playlistToPlaylistForView(interactor.getPlaylist(id)))
        }
    }

    fun getPlaylistId(): Long {
        return playlistLiveData.value!!.id!!
    }

    fun deletePlaylist() {
        viewModelScope.launch {
            interactor.deletePlaylist(getPlaylistId())
            when (val state = tracksInPlaylistLiveData.value) {
                is TracksInPlaylistState.Content -> {
                    state.tracks.forEach {
                        interactor.deleteTrackFromPlaylist(
                            playlistID = getPlaylistId(),
                            trackID = it.trackId,
                        )
                    }
                }
                else -> {}
            }

        }
    }

    fun checkTracksInPlaylist(playlistID: Long) {
        viewModelScope.launch {
            val answer = interactor.getAllTracksInPlaylist(playlistID = playlistID)
            if (answer.isEmpty()) {
                setTracksInPlaylist(TracksInPlaylistState.Empty)
            } else {
                setTracksInPlaylist(TracksInPlaylistState.Content(answer.map {
                    trackMapper.trackToTrackForViewMap(
                        it
                    )
                }))
            }
        }
    }

    fun deleteTrack(trackID: Long, trackIsLiked: Boolean) {
        viewModelScope.launch {
            interactor.deleteTrackFromPlaylist(getPlaylistId(), trackID)
            val playlist = playlistLiveData.value!!
            playlist.tracksCount -= 1
            interactor.updatePlaylist(playlistMapper.playlistForViewToPlaylistMap(playlist))
            setPlaylist(playlist)

            if (!trackIsLiked && !interactor.checkTrackInPlaylists(trackID)) {
                interactor.deleteTrack(trackID)
            }
        }
    }

    fun takePlaylist(): PlaylistForView {
        return playlistLiveData.value!!
    }

    fun takeTracksList(): TracksInPlaylistState {
        return tracksInPlaylistLiveData.value!!
    }
}