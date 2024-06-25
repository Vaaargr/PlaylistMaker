package com.example.playlistmaker.musicLibrary.domain.api.repositorys

import com.example.playlistmaker.musicLibrary.domain.entity.Playlist

interface SavePlaylistRepository {
    suspend fun savePlaylist(playlist: Playlist)
}