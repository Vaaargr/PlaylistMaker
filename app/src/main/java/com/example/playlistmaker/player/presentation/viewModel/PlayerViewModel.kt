package com.example.playlistmaker.player.presentation.viewModel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.player.domain.api.interactors.PlayerInteractor
import com.example.playlistmaker.player.domain.api.interactors.ReceiveTrackUseCase
import com.example.playlistmaker.search.presentation.mappers.TrackViewMapper
import com.example.playlistmaker.search.presentation.model.TrackForView

class PlayerViewModel(
    private val player: PlayerInteractor,
    private val receive: ReceiveTrackUseCase
) : ViewModel() {
    private var isTimer = false
    private var trackDuration = 0
    private val mainHandler = Handler(Looper.getMainLooper())

    private val timerRunnable = Runnable {
        while (isTimer) {
            if (getState() == State.PLAYING) {
                mainHandler.post {
                    setTimer(trackDuration - player.getCurrentPosition())
                }
            }
            Thread.sleep(TIMER_DELAY_MILLIS)
        }
    }

    private val trackLiveData =
        MutableLiveData<TrackForView>()

    private val timerLiveData = MutableLiveData<Int>()

    private val playerStateLiveData = MutableLiveData(State.DEFAULT)

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

    private fun setState(state: State) {
        playerStateLiveData.postValue(state)
    }

    fun getTrack(): TrackForView {
        return trackLiveData.value!!
    }

    fun getTimer(): LiveData<Int> {
        return timerLiveData
    }

    fun getPlayerState(): LiveData<State> {
        return playerStateLiveData
    }

    private fun getState(): State {
        return getPlayerState().value!!
    }

    fun playStopButtonClick() {
        when (getState()) {
            State.PREPARED -> firstPlay()
            State.PLAYING -> playerPause()
            State.PAUSED -> playerPlay()
            State.DEFAULT -> preparePlayer(getTrack().previewUrl)
        }
    }

    private fun firstPlay() {
        isTimer = true
        playerPlay()
        Thread(timerRunnable).start()
    }

    private fun playerPlay() {
        player.start()
        setState(State.PLAYING)
    }

    fun playerPause() {
        player.pause()
        setState(State.PAUSED)
    }

    private fun preparePlayer(url: String) {
        val onPreparedListener = {
            trackDuration = player.getDuration()
            setTimer(player.getDuration() + 1000)
            setState(State.PREPARED)
        }
        val onCompletionListener = {
            isTimer = false
            setState(State.PREPARED)
            setTimer(player.getDuration() + 1000)
        }
        player.preparePlayer(url, onPreparedListener, onCompletionListener)
    }

    fun releasePlayer(){
        isTimer = false
        player.release()
        setState(State.DEFAULT)
    }

    override fun onCleared() {
        super.onCleared()
        isTimer = false
        player.release()
    }

    companion object {
        private const val TIMER_DELAY_MILLIS = 250L
        fun factory(
            player: PlayerInteractor,
            receive: ReceiveTrackUseCase
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    PlayerViewModel(player = player, receive = receive)
                }
            }
        }
    }

    enum class State {
        DEFAULT,
        PREPARED,
        PLAYING,
        PAUSED,
    }
}