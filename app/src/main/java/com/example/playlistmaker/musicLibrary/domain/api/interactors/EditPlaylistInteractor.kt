package com.example.playlistmaker.musicLibrary.domain.api.interactors

import com.example.playlistmaker.musicLibrary.domain.entity.Playlist

interface EditPlaylistInteractor {
    suspend fun savePlaylist(playlist: Playlist)

    suspend fun loadPlaylist(id: Long): Playlist

    suspend fun updatePlaylist(playlist: Playlist)
}