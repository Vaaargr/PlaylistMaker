package com.example.playlistmaker.domain.api.interactors

import com.example.playlistmaker.domain.entity.Track

interface SearchHistoryInteractor {
    fun getHistory(): ArrayList<Track>

    fun saveTrack(track: Track)

    fun clearHistory()
}