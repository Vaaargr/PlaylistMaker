package com.example.playlistmaker.player.domain.api.interactors

import com.example.playlistmaker.search.domain.entity.Track

interface PlayerDatabaseInteractor {
    suspend fun saveTrack(track: Track)

    suspend fun deleteTrack(trackID: Int)

    suspend fun checkTrack(trackID: Int): Boolean
}