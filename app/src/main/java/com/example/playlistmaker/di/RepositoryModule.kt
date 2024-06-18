package com.example.playlistmaker.di

import com.example.playlistmaker.musicLibrary.data.repositorys.ForPlayerDatabaseRepositoryImpl
import com.example.playlistmaker.musicLibrary.data.repositorys.TrackLibraryRepositoryImpl
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.ForPlayerDatabaseRepository
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.TracksLibraryRepository
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
    single<ExternalNavigator> {
        ExternalNavigatorImpl(get())
    }

    single<GetInformationRepository> {
        GetInformationRepositoryImpl(get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get(), get())
    }

    single<SearchTrackRepository> {
        SearchTrackRepositoryImpl(get(), get())
    }

    single<SendTrackRepository> {
        SendTrackRepositoryImpl(get(), get())
    }

    factory<PlayerRepository> {
        PlayerRepositoryImpl(get())
    }

    factory<ReceiveTrackUseCaseRepository> {
        ReceiveTrackUseCaseRepositoryImpl(get(), get())
    }

    single<ForPlayerDatabaseRepository> {
        ForPlayerDatabaseRepositoryImpl(get(), get())
    }

    single<TracksLibraryRepository> {
        TrackLibraryRepositoryImpl(get(), get())
    }
}