package com.example.playlistmaker.di

import com.example.playlistmaker.player.data.repositorys.PlayerRepositoryImpl
import com.example.playlistmaker.player.data.repositorys.ReceiveTrackUseCaseRepositoryImpl
import com.example.playlistmaker.player.domain.api.repositorys.PlayerRepository
import com.example.playlistmaker.player.domain.api.repositorys.ReceiveTrackUseCaseRepository
import com.example.playlistmaker.search.data.repositorys.SearchHistoryRepositoryImpl
import com.example.playlistmaker.search.data.repositorys.SearchTrackRepositoryImpl
import com.example.playlistmaker.search.data.repositorys.SendTrackRepositoryImpl
import com.example.playlistmaker.search.domain.api.repositorys.SearchHistoryRepository
import com.example.playlistmaker.search.domain.api.repositorys.SearchTrackRepository
import com.example.playlistmaker.search.domain.api.repositorys.SendTrackRepository
import com.example.playlistmaker.settings.data.repository.SettingsRepositoryImpl
import com.example.playlistmaker.settings.domain.api.SettingsRepository
import com.example.playlistmaker.sharing.data.repository.ExternalNavigatorImpl
import com.example.playlistmaker.sharing.data.repository.GetInformationRepositoryImpl
import com.example.playlistmaker.sharing.domain.api.ExternalNavigator
import com.example.playlistmaker.sharing.domain.api.GetInformationRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<ExternalNavigator> {
        ExternalNavigatorImpl(get())
    }

    factory<GetInformationRepository> {
        GetInformationRepositoryImpl(get())
    }

    factory<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

    factory<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get())
    }

    factory<SearchTrackRepository> {
        SearchTrackRepositoryImpl(get())
    }

    factory<SendTrackRepository> {
        SendTrackRepositoryImpl(get())
    }

    factory<PlayerRepository> {
        PlayerRepositoryImpl(get())
    }

    factory<ReceiveTrackUseCaseRepository> {
        ReceiveTrackUseCaseRepositoryImpl(get())
    }
}