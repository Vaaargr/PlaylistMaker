package com.example.playlistmaker.main.presenter.state

import com.example.playlistmaker.musicLibrary.MusicLibraryActivity
import com.example.playlistmaker.search.presentation.ui.SearchActivity
import com.example.playlistmaker.settings.presentation.ui.SettingsActivity

sealed interface NavigationState{
    val endPoint: Class<*>
    object Search :NavigationState{
        override val endPoint = SearchActivity::class.java
    }

    object MusicLibrary: NavigationState{
        override val endPoint = MusicLibraryActivity::class.java
    }

    object Settings: NavigationState{
        override val endPoint = SettingsActivity::class.java
    }
}