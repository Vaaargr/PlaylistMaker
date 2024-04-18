package com.example.playlistmaker.di

import com.example.playlistmaker.player.domain.api.interactors.PlayerInteractor
import com.example.playlistmaker.player.domain.api.interactors.ReceiveTrackUseCase
import com.example.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.example.playlistmaker.player.domain.impl.ReceiveTrackUseCaseImpl
import com.example.playlistmaker.search.domain.api.interactors.SearchHistoryInteractor
import com.example.playlistmaker.search.domain.api.interactors.SearchTrackUseCase
import com.example.playlistmaker.search.domain.api.interactors.SendTrackUseCase
import com.example.playlistmaker.search.domain.impl.SearchHistoryInteractorImpl
import com.example.playlistmaker.search.domain.impl.SearchTrackUseCaseImpl
import com.example.playlistmaker.search.domain.impl.SendTrackUseCaseImpl
import com.example.playlistmaker.settings.domain.api.SettingsInteractor
import com.example.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.example.playlistmaker.sharing.domain.api.SharingInteractor
import com.example.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import org.koin.dsl.module

val domainModule = module {

    factory<SharingInteractor> {
        SharingInteractorImpl(get(), get())
    }

    factory<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }

    factory<SearchHistoryInteractor> {
        SearchHistoryInteractorImpl(get())
    }

    factory<SearchTrackUseCase> {
        SearchTrackUseCaseImpl(get())
    }

    factory<SendTrackUseCase> {
        SendTrackUseCaseImpl(get())
    }

    factory<PlayerInteractor> {
        PlayerInteractorImpl(get())
    }

    factory<ReceiveTrackUseCase> {
        ReceiveTrackUseCaseImpl(get())
    }
}