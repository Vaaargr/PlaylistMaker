package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.domain.api.interactors.SaveTrackUseCase
import com.example.playlistmaker.search.domain.entity.Track
import com.example.playlistmaker.search.domain.api.repositorys.SaveTrackRepository

class SaveTrackUseCaseImpl(private val repository: SaveTrackRepository): SaveTrackUseCase{
    override suspend fun saveTrack(track: Track) {
        repository.saveTrack(track)
    }
}