package com.example.playlistmaker.search.domain.api.repositorys

import com.example.playlistmaker.search.domain.entity.Track

interface SearchHistoryRepository {
    fun getHistory(): ArrayList<Track>

    fun clearHistory()

    fun saveHistory(inputHistory: ArrayList<Track>)
}