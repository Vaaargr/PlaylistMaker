package com.example.playlistmaker.musicLibrary.domain.api.interactors

import com.example.playlistmaker.musicLibrary.domain.entity.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistLibraryInteractor {
    fun getSavedPlaylists(): Flow<List<Playlist>>

    suspend fun updatePlaylist(playlist: Playlist)
}