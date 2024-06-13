package com.example.playlistmaker.musicLibrary.domain.api.repositorys

import com.example.playlistmaker.search.domain.entity.Track

interface ForPlayerDatabaseRepository {
    suspend fun saveTrack(track: Track)

    suspend fun deleteTrack(trackID: Int)

    suspend fun checkTrack(trackID: Int): Boolean
}