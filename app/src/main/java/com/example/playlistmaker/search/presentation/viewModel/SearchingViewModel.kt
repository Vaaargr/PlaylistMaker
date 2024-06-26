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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchingViewModel(
    private val sendTrackUseCase: SendTrackUseCase,
    private val searchTrackUseCase: SearchTrackUseCase,
    private val searchHistory: SearchHistoryInteractor,
    private val trackViewMapper: TrackViewMapper,
    private val searchResponseMapper: SearchResponseMapper
) : ViewModel() {

    private val stateLiveData = MutableLiveData<SearchActivityState>(
        SearchActivityState.Response(
            ResponseResult.GOOD,
            emptyList()
        )
    )

    private var searchJob: Job? = null

    private val requestLiveData = MutableLiveData<String>("")

    private val showProgressBarLiveData = MutableLiveData(false)

    fun getState(): LiveData<SearchActivityState> = stateLiveData

    fun getRequest(): String? = requestLiveData.value

    fun observeShowProgressBarLD() : LiveData<Boolean> = showProgressBarLiveData

    private fun setShowProgressBarLD(input: Boolean){
        showProgressBarLiveData.postValue(input)
    }

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

            setShowProgressBarLD(true)

            searchTrackUseCase.execute(getRequest() ?: "")
                .collect { searchResponse ->
                    setState(searchResponseMapper.map(searchResponse))
                }
        }
    }

    fun checkHistory() {
        val history = searchHistory.getHistory()
        if (history.isNotEmpty()) {
            setState(SearchActivityState.History(history.map {
                trackViewMapper.trackToTrackForViewMap(
                    it
                )
            }))
        }
    }

    fun saveTrack(track: TrackForView) {
        searchHistory.saveTrack(trackViewMapper.trackForViewToTrackMap(track))
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
        sendTrackUseCase.execute(trackViewMapper.trackForViewToTrackMap(track))
    }
}