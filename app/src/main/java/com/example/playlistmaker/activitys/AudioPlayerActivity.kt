package com.example.playlistmaker.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import com.example.playlistmaker.enums.Constants
import com.example.playlistmaker.models.Track
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAudioPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            setHomeAsUpIndicator(R.drawable.toolbar_back_arrow)
        }

        val track = Gson().fromJson(intent.getStringExtra(Constants.TRACK.value), Track::class.java)
        val bigAlbumImage = track.artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg")

        Glide.with(this@AudioPlayerActivity)
            .load(bigAlbumImage)
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(2))
            .into(binding.albumImage)

        with(binding) {
            trackName.text = track.trackName
            artistName.text = track.artistName
            duration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
            if(track.collectionName.isNullOrEmpty()){
                albumText.isVisible = false
                album.isVisible = false
            } else album.text = track.collectionName
            year.text = track.releaseDate.subSequence(0,4)
            genre.text = track.primaryGenreName
            country.text = track.country
        }
    }
}