package com.example.playlistmaker.musicLibrary.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.musicLibrary.domain.api.interactors.PlaylistLibraryInteractor
import com.example.playlistmaker.musicLibrary.presentation.mapper.PlaylistForViewMapper
import com.example.playlistmaker.musicLibrary.presentation.states.PlaylistLibraryState
import kotlinx.coroutines.launch

class PlaylistLibraryViewModel(
    private val interactor: PlaylistLibraryInteractor,
    private val mapper: PlaylistForViewMapper
) : ViewModel() {

    private val stateLiveData = MutableLiveData<PlaylistLibraryState>(PlaylistLibraryState.Empty)

    fun observeState(): LiveData<PlaylistLibraryState> = stateLiveData

    private fun setState(state: PlaylistLibraryState) {
        stateLiveData.postValue(state)
    }

    fun checkSavedPlaylists() {
        viewModelScope.launch {
            interactor.getSavedPlaylists().collect { playlists ->
                if (playlists.isEmpty()) {
                    setState(PlaylistLibraryState.Empty)
                } else {
                    setState(PlaylistLibraryState.Content(playlists.map {
                        mapper.playlistToPlaylistForView(
                            it
                        )
                    }))
                }
            }
        }
    }
}