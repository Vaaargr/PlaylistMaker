package com.example.playlistmaker.settings.data.repository

import com.example.playlistmaker.settings.data.clientInterfaces.SettingsClient
import com.example.playlistmaker.settings.domain.api.SettingsRepository
import com.example.playlistmaker.settings.domain.model.ThemeSettings

class SettingsRepositoryImpl(private val client: SettingsClient): SettingsRepository {
    override fun getThemeSettings(): ThemeSettings {
        return client.getThemeSettings()
    }

    override fun changeThemeSettings(settings: ThemeSettings) {
        client.changeThemeSettings(settings)
    }
}