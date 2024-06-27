package com.example.playlistmaker.musicLibrary.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.musicLibrary.domain.api.interactors.GetLikedTracksUseCase
import com.example.playlistmaker.musicLibrary.presentation.states.TracksLibraryState
import com.example.playlistmaker.search.presentation.mappers.TrackViewMapper
import kotlinx.coroutines.launch

class TracksLibraryFragmentViewModel(
    private val getLikedTracksUseCase: GetLikedTracksUseCase,
    private val mapper: TrackViewMapper,
) : ViewModel() {

    private val stateLiveData = MutableLiveData<TracksLibraryState>(TracksLibraryState.Empty)

    fun getStateLiveData(): LiveData<TracksLibraryState> = stateLiveData

    private fun setState(state: TracksLibraryState) {
        stateLiveData.postValue(state)
    }

    fun checkSavedTracks() {
        viewModelScope.launch {
            getLikedTracksUseCase.execute().collect{ tracks ->
                if (tracks.isEmpty()) {
                    setState(TracksLibraryState.Empty)
                } else {
                    setState(TracksLibraryState.Content(tracks.map { mapper.trackToTrackForViewMap(it) }))
                }
            }
        }
    }
}