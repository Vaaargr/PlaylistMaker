package com.example.playlistmaker.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.main.presenter.state.NavigationState
import com.example.playlistmaker.main.presenter.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel.getNavigation().observe(this) { navigationState ->
            goToActivity(navigationState.endPoint)
        }

        with(binding) {
            searchButton.setOnClickListener {
                viewModel.goTo(NavigationState.Search)
            }

            libraryButton.setOnClickListener {
                viewModel.goTo(NavigationState.MusicLibrary)
            }

            settingsButton.setOnClickListener {
                viewModel.goTo(NavigationState.Settings)
            }
        }
    }

    private fun goToActivity(endPoint: Class<*>) {
        startActivity(Intent(this, endPoint))
    }
}
