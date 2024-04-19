package com.example.playlistmaker.musicLibrary.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityMusicLibraryBinding
import com.google.android.material.tabs.TabLayoutMediator

class MusicLibraryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicLibraryBinding
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicLibraryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.toolbar_back_arrow)
        }

        binding.viewPager.adapter = MusicLibraryPagerAdapter(supportFragmentManager, lifecycle)

        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager){tab, position ->
            when(position){
                0 -> tab.text = getText(R.string.featured_tracks)
                else -> tab.text = getText(R.string.playlists)
            }
        }
        tabMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}