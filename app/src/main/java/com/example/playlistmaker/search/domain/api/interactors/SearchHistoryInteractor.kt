package com.example.playlistmaker.search.domain.api.interactors

import com.example.playlistmaker.search.domain.entity.Track

interface SearchHistoryInteractor {
    fun getHistory(): ArrayList<Track>

    fun saveTrack(track: Track)

    fun clearHistory()
}