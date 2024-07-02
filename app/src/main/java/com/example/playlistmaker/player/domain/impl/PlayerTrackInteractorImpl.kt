package com.example.playlistmaker.player.domain.impl

import com.example.playlistmaker.player.domain.api.interactors.PlayerTrackInteractor
import com.example.playlistmaker.player.domain.api.repositorys.PlayerTrackRepository
import com.example.playlistmaker.search.domain.entity.Track

class PlayerTrackInteractorImpl(private val repository: PlayerTrackRepository) :
    PlayerTrackInteractor {

    override suspend fun loadTrack(trackID: Long): Track {
        return repository.loadTrack(trackID)
    }

    override suspend fun updateTrack(track: Track) {
        repository.updateTrack(track)
    }

    override suspend fun deleteTrack(trackID: Long) {
        repository.deleteTrack(trackID)
    }
}