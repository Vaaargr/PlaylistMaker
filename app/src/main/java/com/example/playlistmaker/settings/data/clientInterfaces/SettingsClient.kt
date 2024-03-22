package com.example.playlistmaker.settings.data.clientInterfaces

import com.example.playlistmaker.settings.domain.model.ThemeSettings

interface SettingsClient {
    fun getThemeSettings(): ThemeSettings
    fun changeThemeSettings(settings: ThemeSettings)
}