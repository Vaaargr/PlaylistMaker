package com.example.playlistmaker.player.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.player.domain.api.interactors.PlayerInteractor
import com.example.playlistmaker.player.domain.api.interactors.ReceiveTrackUseCase
import com.example.playlistmaker.player.presentation.PlayerState
import com.example.playlistmaker.search.presentation.mappers.TrackViewMapper
import com.example.playlistmaker.search.presentation.model.TrackForView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val player: PlayerInteractor,
    private val receive: ReceiveTrackUseCase
) : ViewModel() {
    private var isTimer = false
    private var trackDuration = 0

    private val trackLiveData =
        MutableLiveData<TrackForView>()

    private val timerLiveData = MutableLiveData<Int>()

    private val playerStateLiveData: MutableLiveData<PlayerState> =
        MutableLiveData(PlayerState.DEFAULT())

    private fun setTrack(track: TrackForView) {
        trackLiveData.value = track
    }

    init {
        setTrack(TrackViewMapper.trackToTrackForViewMap(receive.execute()))
        preparePlayer(getTrack().previewUrl)
    }

    private fun setTimer(timer: Int) {
        timerLiveData.postValue(timer)
    }

    private fun setState(state: PlayerState) {
        playerStateLiveData.postValue(state)
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

    fun playStopButtonClick() {
        when (getState()) {
            is PlayerState.DEFAULT -> preparePlayer(getTrack().previewUrl)
            is PlayerState.PAUSED -> playerPlay()
            is PlayerState.PLAYING -> playerPause()
            is PlayerState.PREPARED -> firstPlay()
        }
    }

    private fun firstPlay() {
        isTimer = true
        playerPlay()
        viewModelScope.launch {
            while (isTimer) {
                if (getState() is PlayerState.PLAYING) {
                    setTimer(trackDuration - player.getCurrentPosition())
                }
                delay(TIMER_DELAY_MILLIS)
            }
        }
    }

    private fun playerPlay() {
        player.start()
        setState(PlayerState.PLAYING())
    }

    fun playerPause() {
        player.pause()
        setState(PlayerState.PAUSED())
    }

    private fun preparePlayer(url: String) {
        val onPreparedListener = {
            trackDuration = player.getDuration()
            setTimer(player.getDuration())
            setState(PlayerState.PREPARED())
        }
        val onCompletionListener = {
            isTimer = false
            setState(PlayerState.PREPARED())
            //setTimer(player.getDuration())
        }
        player.preparePlayer(url, onPreparedListener, onCompletionListener)
    }

    private fun releasePlayer() {
        isTimer = false
        player.release()
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }

    companion object {
        private const val TIMER_DELAY_MILLIS = 300L
    }
}