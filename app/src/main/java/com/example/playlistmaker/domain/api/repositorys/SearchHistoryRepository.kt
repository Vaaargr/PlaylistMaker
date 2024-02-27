package com.example.playlistmaker.domain.api.repositorys

import com.example.playlistmaker.domain.entity.Track

interface SearchHistoryRepository {
    fun getHistory(): ArrayList<Track>

    fun clearHistory()

    fun saveHistory(inputHistory: ArrayList<Track>)
}