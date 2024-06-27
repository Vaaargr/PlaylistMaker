package com.example.playlistmaker.di

import com.example.playlistmaker.musicLibrary.domain.api.interactors.PlaylistInteractor
import com.example.playlistmaker.musicLibrary.domain.api.interactors.PlaylistLibraryInteractor
import com.example.playlistmaker.musicLibrary.domain.api.interactors.GetLikedTracksUseCase
import com.example.playlistmaker.musicLibrary.domain.api.interactors.EditPlaylistInteractor
import com.example.playlistmaker.musicLibrary.domain.impl.PlaylistInteractorImpl
import com.example.playlistmaker.musicLibrary.domain.impl.PlaylistLibraryInteractorImpl
import com.example.playlistmaker.musicLibrary.domain.impl.GetLikedTracksUseCaseImpl
import com.example.playlistmaker.musicLibrary.domain.impl.EditPlaylistInteractorImpl
import com.example.playlistmaker.player.domain.api.interactors.PlayerInteractor
import com.example.playlistmaker.player.domain.api.interactors.PlayerPlaylistTrackInteractor
import com.example.playlistmaker.player.domain.api.interactors.PlayerTrackInteractor
import com.example.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.example.playlistmaker.player.domain.impl.PlayerPlaylistTrackInteractorImpl
import com.example.playlistmaker.player.domain.impl.PlayerTrackInteractorImpl
import com.example.playlistmaker.search.domain.api.interactors.SearchHistoryInteractor
import com.example.playlistmaker.search.domain.api.interactors.SearchTrackUseCase
import com.example.playlistmaker.search.domain.api.interactors.SaveTrackUseCase
import com.example.playlistmaker.search.domain.impl.SearchHistoryInteractorImpl
import com.example.playlistmaker.search.domain.impl.SearchTrackUseCaseImpl
import com.example.playlistmaker.search.domain.impl.SaveTrackUseCaseImpl
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

    factory<SaveTrackUseCase> {
        SaveTrackUseCaseImpl(get())
    }

    factory<PlayerInteractor> {
        PlayerInteractorImpl(get())
    }

    factory<PlayerTrackInteractor> {
        PlayerTrackInteractorImpl(get())
    }

    factory<GetLikedTracksUseCase> {
        GetLikedTracksUseCaseImpl(get())
    }

    factory<EditPlaylistInteractor> {
        EditPlaylistInteractorImpl(get())
    }

    factory<PlaylistLibraryInteractor>{
        PlaylistLibraryInteractorImpl(get())
    }

    factory<PlaylistInteractor>{
        PlaylistInteractorImpl(get())
    }

    factory<PlayerPlaylistTrackInteractor> {
        PlayerPlaylistTrackInteractorImpl(get())
    }
}