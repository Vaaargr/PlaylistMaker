package com.example.playlistmaker.player.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import com.example.playlistmaker.player.presentation.states.SavedTrackState
import com.example.playlistmaker.tools.GlideClient
import com.example.playlistmaker.player.presentation.viewModel.PlayerViewModel
import com.example.playlistmaker.tools.Formatter
import org.koin.androidx.viewmodel.ext.android.viewModel

class AudioPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAudioPlayerBinding
    private val viewModel by viewModel<PlayerViewModel>()

    private val imageLoader = GlideClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val track = viewModel.getTrack()

        viewModel.getPlayerState().observe(this) { state ->
            changeButton(state.buttonState)
        }

        viewModel.getTimer().observe(this) { time ->
            binding.timerText.text = Formatter.timeFormat(time)
        }

        viewModel.getSavedTrackState().observe(this){state ->
            when(state){
                SavedTrackState.savedTrack -> binding.likeButton.setImageResource(R.drawable.saved_heart)
                SavedTrackState.unsavedTrack -> binding.likeButton.setImageResource(R.drawable.unsaved_heart)
            }
        }

        imageLoader.loadImage(
            this@AudioPlayerActivity,
            track.artworkUrl512,
            resources.getDimensionPixelSize(R.dimen.big_corner_radius),
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
            viewModel.playStopButtonClick()
        }

        binding.backButton.setOnClickListener {
            this@AudioPlayerActivity.onBackPressedDispatcher.onBackPressed()
        }

        binding.likeButton.setOnClickListener {
            viewModel.lickButtonClick()
        }
    }

    private fun changeButton(isPlayed: Boolean) {
        binding.playButton.setImageResource(if (isPlayed) R.drawable.pause else R.drawable.play)
    }

    override fun onPause() {
        super.onPause()
        viewModel.playerPause()
    }
}