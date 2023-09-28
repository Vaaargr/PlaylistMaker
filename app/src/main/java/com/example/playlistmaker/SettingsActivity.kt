package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        with(binding) {
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