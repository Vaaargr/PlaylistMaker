package com.example.playlistmaker.musicLibrary.domain.impl

import com.example.playlistmaker.musicLibrary.domain.api.interactors.SavePlaylistUseCase
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.SavePlaylistRepository
import com.example.playlistmaker.musicLibrary.domain.entity.Playlist

class SavePlaylistUseCaseImpl(private val playlistsLibraryRepository: SavePlaylistRepository): SavePlaylistUseCase {
    override suspend fun execute(playlist: Playlist) {
        playlistsLibraryRepository.savePlaylist(playlist)
    }
}