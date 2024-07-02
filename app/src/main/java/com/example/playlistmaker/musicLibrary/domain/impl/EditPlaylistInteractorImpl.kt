package com.example.playlistmaker.musicLibrary.domain.impl

import com.example.playlistmaker.musicLibrary.domain.api.interactors.EditPlaylistInteractor
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.EditPlaylistRepository
import com.example.playlistmaker.musicLibrary.domain.entity.Playlist

class EditPlaylistInteractorImpl(private val editPlaylistRepository: EditPlaylistRepository): EditPlaylistInteractor {
    override suspend fun savePlaylist(playlist: Playlist) {
        editPlaylistRepository.savePlaylist(playlist)
    }

    override suspend fun loadPlaylist(id: Long): Playlist {
        return editPlaylistRepository.loadPlaylist(id)
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        editPlaylistRepository.updatePlaylist(playlist)
    }
}