package com.example.playlistmaker.settings.data.clients

import android.content.SharedPreferences
import com.example.playlistmaker.settings.data.clientInterfaces.SettingsClient
import com.example.playlistmaker.settings.domain.model.ThemeSettings
import com.google.gson.Gson

class SettingsShPrefClient(private val prefs: SharedPreferences, private val key: String): SettingsClient {
    override fun getThemeSettings(): ThemeSettings {
        val json = prefs.getString(key, " ")
        return if (json != " "){
            Gson().fromJson(json, ThemeSettings::class.java)
        } else {
            ThemeSettings(false)
        }
    }

    override fun changeThemeSettings(settings: ThemeSettings) {
        prefs.edit().putString(key, Gson().toJson(settings)).apply()
    }
}