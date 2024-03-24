package com.example.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.di.dataModule
import com.example.playlistmaker.di.domainModule
import com.example.playlistmaker.di.repositoryModule
import com.example.playlistmaker.di.viewModelModule
import com.example.playlistmaker.settings.domain.model.ThemeSettings
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    private lateinit var shPref: SharedPreferences
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, domainModule, repositoryModule, viewModelModule)
        }

        shPref =
            getSharedPreferences(resources.getString(R.string.sh_prefs_name), MODE_PRIVATE)

        switchTheme(getThemeSettings())

        listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == resources.getString(R.string.theme_key)) {
                switchTheme(getThemeSettings())
            }
        }
        shPref.registerOnSharedPreferenceChangeListener(listener)
    }

    private fun getThemeSettings(): ThemeSettings {
        val json = shPref.getString(resources.getString(R.string.theme_key), null)
        return if (json.isNullOrEmpty()) {
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
}