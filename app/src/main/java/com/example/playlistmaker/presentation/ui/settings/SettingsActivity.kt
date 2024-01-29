package com.example.playlistmaker.presentation.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.enums.ThemeDictionary

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var shPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shPref = getSharedPreferences(ThemeDictionary.SHARED_PREFS_SETTINGS_NAME.value, MODE_PRIVATE)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.toolbar_back_arrow)
        }

        with(binding) {
            darkThemeSwitch.isChecked = shPref.getBoolean(ThemeDictionary.THEME_MODE.value, false)
            darkThemeSwitch.setOnCheckedChangeListener { _, isChecked ->
                shPref.edit().putBoolean(ThemeDictionary.THEME_MODE.value, isChecked).apply()
            }

            shareAppButton.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_address))
                startActivity(Intent.createChooser(shareIntent, ""))
            }

            supportButton.setOnClickListener {
                val supportIntent = Intent(Intent.ACTION_SENDTO)
                supportIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    getString(R.string.message_subject)
                )
                supportIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    getString(R.string.message_body)
                )
                supportIntent.data = Uri.parse(getString(R.string.user_mail))
                startActivity(supportIntent)
            }

            termsButton.setOnClickListener {
                val termIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.term_address))
                )
                startActivity(termIntent)
            }
        }
    }
}