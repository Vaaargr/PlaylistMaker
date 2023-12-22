package com.example.playlistmaker.activitys

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.Tools
import com.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import com.example.playlistmaker.enums.ConstantsKey
import com.example.playlistmaker.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAudioPlayerBinding
    private lateinit var timerRunnable: Runnable
    private lateinit var track: Track
    private val mainHandler = Handler(Looper.getMainLooper())
    private var trackDuration = 0
    private var mediaPlayer = MediaPlayer()
    private var playerState = STATE_DEFAULT
    private var isTimer = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        track = intent.getSerializableExtra(ConstantsKey.TRACK.value) as Track
        val roundingRadius = Tools().dpToPx(8F, this)

        preparePlayer(track.previewUrl)

        timerRunnable = Runnable {
            while (isTimer) {
                if (playerState == STATE_PLAYING) {
                    mainHandler.post {
                        binding.timerText.text =
                            SimpleDateFormat("mm:ss", Locale.getDefault()).format(
                                trackDuration - mediaPlayer.currentPosition
                            )
                    }
                }
                Thread.sleep(TIMER_DELAY)
            }
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            setHomeAsUpIndicator(R.drawable.toolbar_back_arrow)
        }

        Glide.with(this@AudioPlayerActivity)
            .load(track.getBigAlbumImage())
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(roundingRadius))
            .into(binding.albumImage)

        with(binding) {
            trackName.text = track.trackName
            artistName.text = track.artistName
            duration.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
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
                STATE_PREPARED -> firstPlay()
                STATE_PLAYING -> playerPause()
                STATE_PAUSED -> playerPlay()
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
        mediaPlayer.release()
    }

    private fun firstPlay() {
        isTimer = true
        playerPlay()
        Thread(timerRunnable).start()
    }

    private fun playerPlay() {
        mediaPlayer.start()
        playerState = STATE_PLAYING
        changeButton(true)
    }

    private fun playerPause() {
        mediaPlayer.pause()
        playerState = STATE_PAUSED
        changeButton(false)
    }


    private fun changeButton(isPlayed: Boolean) {
        binding.playButton.setImageResource(if (isPlayed) R.drawable.pause else R.drawable.play)
    }

    private fun preparePlayer(url: String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            isTimer = true
            playerState = STATE_PREPARED
            trackDuration = mediaPlayer.duration
        }
        mediaPlayer.setOnCompletionListener {
            isTimer = false
            playerState = STATE_PREPARED
            binding.timerText.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(0)
            changeButton(false)
        }
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val TIMER_DELAY = 250L
    }
}