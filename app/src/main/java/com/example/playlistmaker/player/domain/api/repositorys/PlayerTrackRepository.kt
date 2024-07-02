package com.example.playlistmaker.player.domain.api.repositorys

import com.example.playlistmaker.search.domain.entity.Track

interface PlayerTrackRepository {

    suspend fun loadTrack(trackID: Long): Track

    suspend fun updateTrack(track: Track)

    suspend fun deleteTrack(trackID: Long)
}