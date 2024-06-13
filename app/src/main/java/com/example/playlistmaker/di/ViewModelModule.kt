package com.example.playlistmaker.di

import com.example.playlistmaker.musicLibrary.presentation.viewModel.EmptyPlaylistsFragmentViewModel
import com.example.playlistmaker.musicLibrary.presentation.viewModel.TracksLibraryFragmentViewModel
import com.example.playlistmaker.player.presentation.viewModel.PlayerViewModel
import com.example.playlistmaker.search.presentation.viewModel.SearchingViewModel
import com.example.playlistmaker.settings.presentation.viewModel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SettingsViewModel(get(), get())
    }

    viewModel {
        SearchingViewModel(get(), get(), get(), get(), get())
    }

    viewModel {
        PlayerViewModel(get(), get(), get(), get())
    }

    viewModel {
        TracksLibraryFragmentViewModel(get(), get(), get())
    }

    viewModel {
        EmptyPlaylistsFragmentViewModel()
    }
}