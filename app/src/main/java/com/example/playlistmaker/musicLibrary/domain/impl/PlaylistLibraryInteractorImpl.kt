package com.example.playlistmaker.musicLibrary.domain.impl

import com.example.playlistmaker.musicLibrary.domain.api.interactors.PlaylistLibraryInteractor
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.PlaylistLibraryRepository
import com.example.playlistmaker.musicLibrary.domain.entity.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistLibraryInteractorImpl(private val repository: PlaylistLibraryRepository): PlaylistLibraryInteractor{
    override fun getSavedPlaylists(): Flow<List<Playlist>> {
        return repository.getSavedPlaylists()
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        repository.updatePlaylist(playlist)
    }
}