package com.example.playlistmaker.musicLibrary.domain.api.interactors

import com.example.playlistmaker.musicLibrary.domain.entity.Playlist

interface SavePlaylistUseCase {
    suspend fun execute(playlist: Playlist)
}