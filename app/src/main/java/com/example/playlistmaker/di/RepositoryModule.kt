package com.example.playlistmaker.di

import com.example.playlistmaker.musicLibrary.data.repositorys.PlaylistLibraryRepositoryImpl
import com.example.playlistmaker.musicLibrary.data.repositorys.PlaylistRepositoryImpl
import com.example.playlistmaker.musicLibrary.data.repositorys.EditPlaylistRepositoryImpl
import com.example.playlistmaker.musicLibrary.data.repositorys.PlayerPlaylistTrackRepositoryImpl
import com.example.playlistmaker.musicLibrary.data.repositorys.PlayerTrackRepositoryImpl
import com.example.playlistmaker.musicLibrary.data.repositorys.TrackLibraryRepositoryImpl
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.PlaylistLibraryRepository
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.PlaylistRepository
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.EditPlaylistRepository
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.TracksLibraryRepository
import com.example.playlistmaker.player.data.repositorys.PlayerRepositoryImpl
import com.example.playlistmaker.player.domain.api.repositorys.PlayerPlaylistTrackRepository
import com.example.playlistmaker.player.domain.api.repositorys.PlayerRepository
import com.example.playlistmaker.player.domain.api.repositorys.PlayerTrackRepository
import com.example.playlistmaker.search.data.repositorys.SearchHistoryRepositoryImpl
import com.example.playlistmaker.search.data.repositorys.SearchTrackRepositoryImpl
import com.example.playlistmaker.search.data.repositorys.SaveTrackRepositoryImpl
import com.example.playlistmaker.search.domain.api.repositorys.SearchHistoryRepository
import com.example.playlistmaker.search.domain.api.repositorys.SearchTrackRepository
import com.example.playlistmaker.search.domain.api.repositorys.SaveTrackRepository
import com.example.playlistmaker.settings.data.repository.SettingsRepositoryImpl
import com.example.playlistmaker.settings.domain.api.SettingsRepository
import com.example.playlistmaker.sharing.data.clients.ExternalNavigatorClientImpl
import com.example.playlistmaker.sharing.data.clientsInterfaces.ExternalNavigatorClient
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

    single<SaveTrackRepository> {
        SaveTrackRepositoryImpl(get(), get())
    }

    factory<PlayerRepository> {
        PlayerRepositoryImpl(get())
    }

    factory<PlayerTrackRepository> {
        PlayerTrackRepositoryImpl(get(), get())
    }

    single<TracksLibraryRepository> {
        TrackLibraryRepositoryImpl(get(), get())
    }

    single<EditPlaylistRepository> {
        EditPlaylistRepositoryImpl(get(), get())
    }

    single<PlaylistLibraryRepository>{
        PlaylistLibraryRepositoryImpl(get(), get())
    }

    single<PlaylistRepository> {
        PlaylistRepositoryImpl(get(), get(), get())
    }

    single<PlayerPlaylistTrackRepository> {
        PlayerPlaylistTrackRepositoryImpl(get())
    }
}