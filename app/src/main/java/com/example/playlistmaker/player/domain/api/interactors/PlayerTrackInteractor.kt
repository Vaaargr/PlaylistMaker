package com.example.playlistmaker.player.domain.api.interactors

import com.example.playlistmaker.search.domain.entity.Track

interface PlayerTrackInteractor {
    suspend fun loadTrack(trackID: Long): Track

    suspend fun updateTrack(track: Track)

    suspend fun deleteTrack(trackID: Long)
}