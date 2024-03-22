package com.example.playlistmaker.settings.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.R
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.settings.presentation.viewModel.SettingsViewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            SettingsViewModel.factory(
                Creator.getSharingInteractor(),
                Creator.getSettingsInteractor()
            )
        )[SettingsViewModel::class.java]

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.toolbar_back_arrow)
        }

        viewModel.getThemeSettings().observe(this) { themeSettings ->
            binding.darkThemeSwitch.isChecked = themeSettings.nightTheme
        }

        with(binding) {
            darkThemeSwitch.setOnCheckedChangeListener { _, isChecked ->
                viewModel.changeNightMode(isChecked)
            }

            shareAppButton.setOnClickListener {
                viewModel.shareApp()
            }

            supportButton.setOnClickListener {
                viewModel.openSupport()
            }

            termsButton.setOnClickListener {
                viewModel.openTerms()
            }
        }
    }
}