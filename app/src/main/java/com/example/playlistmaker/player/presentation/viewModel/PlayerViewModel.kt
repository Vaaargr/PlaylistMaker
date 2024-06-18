package com.example.playlistmaker.player.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.player.domain.api.interactors.PlayerDatabaseInteractor
import com.example.playlistmaker.player.domain.api.interactors.PlayerInteractor
import com.example.playlistmaker.player.domain.api.interactors.ReceiveTrackUseCase
import com.example.playlistmaker.player.presentation.states.PlayerState
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
    private val mapper: TrackViewMapper
) : ViewModel() {
    private var timerJob: Job? = null

    private val trackLiveData =
        MutableLiveData<TrackForView>()

    private val timerLiveData = MutableLiveData<Int>()

    private val playerStateLiveData: MutableLiveData<PlayerState> =
        MutableLiveData(PlayerState.DEFAULT())

    private val savedTrackStateLiveData = MutableLiveData<SavedTrackState>()

    private fun setTrack(track: TrackForView) {
        trackLiveData.value = track
    }

    init {
        setTrack(mapper.trackToTrackForViewMap(receive.execute()))
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

    private fun releasePlayer() {
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
                setSavedTrackState(SavedTrackState.savedTrack)
            } else {
                setSavedTrackState(SavedTrackState.unsavedTrack)
            }
        }
    }

    fun lickButtonClick(){
        when(savedTrackStateLiveData.value!!){
            SavedTrackState.savedTrack -> deleteTrack()
            SavedTrackState.unsavedTrack -> saveTrack()
        }
    }

    private fun saveTrack() {
        viewModelScope.launch {
            playerDBInteractor.saveTrack(mapper.trackForViewToTrackMap(trackLiveData.value!!))
            setSavedTrackState(SavedTrackState.savedTrack)
        }
    }

    private fun deleteTrack(){
        viewModelScope.launch {
            playerDBInteractor.deleteTrack(trackLiveData.value!!.trackId)
            setSavedTrackState(SavedTrackState.unsavedTrack)
        }
    }

    companion object {
        private const val TIMER_DELAY_MILLIS = 300L
    }
}