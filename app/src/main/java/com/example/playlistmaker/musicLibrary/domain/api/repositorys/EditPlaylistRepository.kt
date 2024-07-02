package com.example.playlistmaker.musicLibrary.domain.api.repositorys

import com.example.playlistmaker.musicLibrary.domain.entity.Playlist

interface EditPlaylistRepository {
    suspend fun savePlaylist(playlist: Playlist)

    suspend fun loadPlaylist(id: Long): Playlist

    suspend fun updatePlaylist(playlist: Playlist)
}