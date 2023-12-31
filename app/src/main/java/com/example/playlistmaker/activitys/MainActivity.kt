package com.example.playlistmaker.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Toast
import com.example.playlistmaker.App
import com.example.playlistmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var app = App()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        with(binding) {
            searchButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
            }

            libraryButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, MusicLibraryActivity::class.java))
            }

            settingsButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            }
        }
    }
}
