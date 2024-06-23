package com.example.playlistmaker.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.frag_container) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener{_, destination,_ ->
            when(destination.id){
                R.id.newPlaylistFragment, R.id.audioPlayerFragment-> binding.bottomNavView.isVisible = false
                else -> binding.bottomNavView.isVisible = true
            }
        }
        binding.bottomNavView.setupWithNavController(navController)
    }
}
