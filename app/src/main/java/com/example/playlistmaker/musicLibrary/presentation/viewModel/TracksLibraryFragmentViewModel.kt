package com.example.playlistmaker.musicLibrary.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.musicLibrary.domain.api.interactors.ReceiveSavedTracksUseCase
import com.example.playlistmaker.musicLibrary.presentation.states.TracksLibraryState
import com.example.playlistmaker.search.domain.api.interactors.SendTrackUseCase
import com.example.playlistmaker.search.presentation.mappers.TrackViewMapper
import com.example.playlistmaker.search.presentation.model.TrackForView
import kotlinx.coroutines.launch

class TracksLibraryFragmentViewModel(
    private val receiveSavedTracks: ReceiveSavedTracksUseCase,
    private val mapper: TrackViewMapper,
    private val sendTrackUseCase: SendTrackUseCase
) : ViewModel() {

    private val stateLiveData = MutableLiveData<TracksLibraryState>(TracksLibraryState.Empty)

    fun getStateLiveData(): LiveData<TracksLibraryState> = stateLiveData

    private fun setState(state: TracksLibraryState) {
        stateLiveData.postValue(state)
    }

    fun checkSavedTracks() {
        viewModelScope.launch {
            receiveSavedTracks.execute().collect{ tracks ->
                if (tracks.isEmpty()) {
                    setState(TracksLibraryState.Empty)
                } else {
                    setState(TracksLibraryState.Content(tracks.map { mapper.trackToTrackForViewMap(it) }))
                }
            }
        }
    }

    fun sendTrack(track: TrackForView){
        sendTrackUseCase.execute(mapper.trackForViewToTrackMap(track))
    }
}