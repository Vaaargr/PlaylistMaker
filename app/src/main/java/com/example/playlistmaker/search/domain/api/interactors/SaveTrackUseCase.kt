package com.example.playlistmaker.search.domain.api.interactors

import com.example.playlistmaker.search.domain.entity.Track

interface SaveTrackUseCase {
    suspend fun saveTrack(track: Track)
}