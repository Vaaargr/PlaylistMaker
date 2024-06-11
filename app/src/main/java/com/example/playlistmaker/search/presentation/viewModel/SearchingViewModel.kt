package com.example.playlistmaker.search.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.search.domain.api.interactors.SearchHistoryInteractor
import com.example.playlistmaker.search.domain.api.interactors.SearchTrackUseCase
import com.example.playlistmaker.search.domain.api.interactors.SendTrackUseCase
import com.example.playlistmaker.search.presentation.mappers.SearchResponseMapper
import com.example.playlistmaker.search.presentation.mappers.TrackViewMapper
import com.example.playlistmaker.search.presentation.model.ResponseResult
import com.example.playlistmaker.search.presentation.model.TrackForView
import com.example.playlistmaker.search.presentation.state.SearchActivityState
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchingViewModel(
    private val sendTrackUseCase: SendTrackUseCase,
    private val searchTrackUseCase: SearchTrackUseCase,
    private val searchHistory: SearchHistoryInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<SearchActivityState>(
        SearchActivityState.Response(
            ResponseResult.GOOD,
            emptyList()
        )
    )

    private var searchJob: Job? = null

    private val requestLiveData = MutableLiveData<String>("")

    fun getState(): LiveData<SearchActivityState> = stateLiveData

    fun getRequest(): String? = requestLiveData.value

    private fun setState(state: SearchActivityState) {
        stateLiveData.postValue(state)
    }

    private fun setRequest(request: String) {
        requestLiveData.postValue(request)
    }

    fun changeRequest(request: String): Boolean {
        return if (request.isNotEmpty() && request != getRequest()) {
            setRequest(request)
            true
        } else {
            false
        }
    }

    fun clearRequest() {
        setRequest("")
    }

    fun cancelSearch(){
        searchJob?.cancel()
    }

    fun search(searchDelay: Long) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(searchDelay)

            searchTrackUseCase.execute(getRequest() ?: "")
                .collect { searchResponse ->
                    setState(SearchResponseMapper.map(searchResponse))
                }
        }
    }

    fun checkHistory() {
        val history = searchHistory.getHistory()
        if (history.isNotEmpty()) {
            setState(SearchActivityState.History(history.map {
                TrackViewMapper.trackToTrackForViewMap(
                    it
                )
            }))
        }
    }

    fun saveTrack(track: TrackForView) {
        searchHistory.saveTrack(TrackViewMapper.trackForViewToTrackMap(track))
    }

    fun clearHistory() {
        searchHistory.clearHistory()
        setState(
            SearchActivityState.Response(
                ResponseResult.GOOD,
                emptyList()
            )
        )
    }

    fun sendTrack(track: TrackForView) {
        sendTrackUseCase.execute(TrackViewMapper.trackForViewToTrackMap(track))
    }
}