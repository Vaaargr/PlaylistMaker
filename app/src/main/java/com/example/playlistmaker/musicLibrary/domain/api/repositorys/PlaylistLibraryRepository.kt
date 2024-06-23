package com.example.playlistmaker.musicLibrary.domain.api.repositorys

import com.example.playlistmaker.musicLibrary.domain.entity.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistLibraryRepository {
    fun getSavedPlaylists(): Flow<List<Playlist>>

    suspend fun updatePlaylist(playlist: Playlist)
}