package com.example.playlistmaker.tools

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.TypedValue
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.enums.ThemeDictionary

class App : Application() {
    private lateinit var shPref: SharedPreferences
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
    override fun onCreate() {
        super.onCreate()
        shPref =
            getSharedPreferences(ThemeDictionary.SHARED_PREFS_SETTINGS_NAME.value, MODE_PRIVATE)

        Creator.init(shPref)

        switchTheme(shPref.getBoolean(ThemeDictionary.THEME_MODE.value, false))

        listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == ThemeDictionary.THEME_MODE.value) {
                switchTheme(sharedPreferences.getBoolean(key, false))
            }
        }
        shPref.registerOnSharedPreferenceChangeListener(listener)
    }

    private fun switchTheme(themeMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (themeMode) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}