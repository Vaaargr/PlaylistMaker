package com.example.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.settings.domain.model.ThemeSettings
import com.google.gson.Gson

class App : Application() {
    private lateinit var shPref: SharedPreferences
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
    override fun onCreate() {
        super.onCreate()
        shPref =
            getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)

        Creator.init(shPref,  THEME_KEY, TRACK_KEY, this)

        switchTheme(getThemeSettings())

        listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == THEME_KEY) {
                switchTheme(getThemeSettings())
            }
        }
        shPref.registerOnSharedPreferenceChangeListener(listener)
    }

    private fun getThemeSettings(): ThemeSettings{
        val json = shPref.getString(THEME_KEY, null)
        return if (json.isNullOrEmpty()){
            ThemeSettings(false)
        } else {
            Gson().fromJson(json, ThemeSettings::class.java)
        }
    }

    private fun switchTheme(themeMode: ThemeSettings?) {
        val input = themeMode?.nightTheme ?: false
        AppCompatDelegate.setDefaultNightMode(
            if (input) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    companion object{
        const val SHARED_PREFS_NAME = "shared prefs name"
        const val THEME_KEY = "theme key"
        const val TRACK_KEY = "track key"
    }
}