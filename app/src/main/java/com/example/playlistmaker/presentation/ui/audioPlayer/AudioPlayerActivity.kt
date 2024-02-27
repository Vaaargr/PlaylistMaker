package com.example.playlistmaker.presentation.ui.audioPlayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import com.example.playlistmaker.presentation.GlideClient
import com.example.playlistmaker.presentation.mappers.TrackViewMapper
import com.example.playlistmaker.tools.Creator
import com.example.playlistmaker.tools.Formatter

class AudioPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAudioPlayerBinding

    private val trackExchangeInteractImpl = Creator.getTrackExchangeInteractImpl()
    private val track =
        TrackViewMapper.trackToTrackForViewMap(trackExchangeInteractImpl.receiveTrack())
    private val imageLoader = GlideClient()
    private val playerInteractor = Creator.getPlayerInteractor()

    private val mainHandler = Handler(Looper.getMainLooper())
    private var trackDuration = 0
    private var playerState = State.DEFAULT
    private var isTimer = false

    private val timerRunnable = Runnable {
        while (isTimer) {
            if (playerState == State.PLAYING) {
                mainHandler.post {
                    binding.timerText.text =
                        Formatter.timeFormat(trackDuration - playerInteractor.getCurrentPosition())
                }
            }
            Thread.sleep(TIMER_DELAY_MILLIS)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preparePlayer(track.previewUrl)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            setHomeAsUpIndicator(R.drawable.toolbar_back_arrow)
        }

        imageLoader.loadImage(
            this@AudioPlayerActivity,
            track.artworkUrl512,
            ROUNDING_RADIUS,
            binding.albumImage
        )

        with(binding) {
            trackName.text = track.trackName
            artistName.text = track.artistName
            duration.text = track.trackTimeString
            if (track.collectionName.isNullOrEmpty()) {
                albumText.isVisible = false
                album.isVisible = false
            } else album.text = track.collectionName
            year.text = track.releaseDate.subSequence(0, 4)
            genre.text = track.primaryGenreName
            country.text = track.country
        }

        binding.playButton.setOnClickListener {
            when (playerState) {
                State.PREPARED -> firstPlay()
                State.PLAYING -> playerPause()
                State.PAUSED -> playerPlay()
                State.DEFAULT -> preparePlayer(track.previewUrl)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        playerPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        isTimer = false
        playerInteractor.release()
    }

    private fun firstPlay() {
        isTimer = true
        playerPlay()
        Thread(timerRunnable).start()
    }

    private fun playerPlay() {
        playerInteractor.start()
        playerState = State.PLAYING
        changeButton(true)
    }

    private fun playerPause() {
        playerInteractor.pause()
        playerState = State.PAUSED
        changeButton(false)
    }


    private fun changeButton(isPlayed: Boolean) {
        binding.playButton.setImageResource(if (isPlayed) R.drawable.pause else R.drawable.play)
    }

    private fun preparePlayer(url: String) {
        val onPreparedListener = {
            isTimer = true
            playerState = State.PREPARED
            trackDuration = playerInteractor.getDuration()
        }
        val onCompletionListener = {
            isTimer = false
            playerState = State.PREPARED
            binding.timerText.text = Formatter.timeFormat(0)
            changeButton(false)
        }
        playerInteractor.preparePlayer(url, onPreparedListener, onCompletionListener)
    }

    companion object {
        private const val TIMER_DELAY_MILLIS = 250L
        private const val ROUNDING_RADIUS = 8.0F
    }

    enum class State {
        DEFAULT,
        PREPARED,
        PLAYING,
        PAUSED,
    }
}