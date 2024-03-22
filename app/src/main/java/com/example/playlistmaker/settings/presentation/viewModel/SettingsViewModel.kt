package com.example.playlistmaker.settings.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.settings.domain.api.SettingsInteractor
import com.example.playlistmaker.settings.domain.model.ThemeSettings
import com.example.playlistmaker.sharing.domain.api.SharingInteractor
import com.example.playlistmaker.tools.SingleEventLiveData

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {
    private val themeSettingsLiveData = SingleEventLiveData<ThemeSettings>()

    init {
        themeSettingsLiveData.setValue(settingsInteractor.getThemeSettings())
    }

    fun getThemeSettings(): LiveData<ThemeSettings> = themeSettingsLiveData

    private fun setThemeSettings(settings: ThemeSettings) {
        themeSettingsLiveData.setValue(settings)
        settingsInteractor.updateThemeSettings(settings)
    }

    fun changeNightMode(nightModeTrigger: Boolean) {
        setThemeSettings(ThemeSettings(nightModeTrigger))
    }

    fun shareApp(){
        sharingInteractor.shareApp()
    }

    fun openTerms(){
        sharingInteractor.openTerms()
    }

    fun openSupport(){
        sharingInteractor.openSupport()
    }

    companion object {
        fun factory(
            sharingInteractor: SharingInteractor,
            settingsInteractor: SettingsInteractor,
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    SettingsViewModel(sharingInteractor, settingsInteractor)
                }
            }
        }
    }
}