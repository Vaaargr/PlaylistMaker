package com.example.playlistmaker.settings.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.settings.presentation.viewModel.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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