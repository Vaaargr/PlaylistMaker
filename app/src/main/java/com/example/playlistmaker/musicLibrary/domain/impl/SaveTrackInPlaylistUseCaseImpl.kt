package com.example.playlistmaker.musicLibrary.domain.impl

import com.example.playlistmaker.musicLibrary.domain.api.interactors.SaveTrackInPlaylistUseCase
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.SaveTrackInPlaylistRepository
import com.example.playlistmaker.search.domain.entity.Track

class SaveTrackInPlaylistUseCaseImpl(private val saveTrackInPlaylistRepository: SaveTrackInPlaylistRepository): SaveTrackInPlaylistUseCase {
    override suspend fun execute(track: Track) {
        saveTrackInPlaylistRepository.saveTrack(track)
    }
}