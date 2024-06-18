package com.example.playlistmaker.player.domain.impl

import com.example.playlistmaker.musicLibrary.domain.api.repositorys.ForPlayerDatabaseRepository
import com.example.playlistmaker.player.domain.api.interactors.PlayerDatabaseInteractor
import com.example.playlistmaker.search.domain.entity.Track

class PlayerDatabaseInteractorImpl(private val dbRepository: ForPlayerDatabaseRepository) :
    PlayerDatabaseInteractor {
    override suspend fun saveTrack(track: Track) {
        dbRepository.saveTrack(track)
    }

    override suspend fun deleteTrack(trackID: Int) {
        dbRepository.deleteTrack(trackID)
    }

    override suspend fun checkTrack(trackID: Int): Boolean {
        return dbRepository.checkTrack(trackID)
    }
}