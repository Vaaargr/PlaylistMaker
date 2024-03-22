package com.example.playlistmaker.search.data.clientInterfaces

import com.example.playlistmaker.search.data.dto.TrackDto

interface SearchHistoryClient {
    fun getHistory(): ArrayList<TrackDto>

     fun clearHistory()

     fun saveHistory(inputHistory: ArrayList<TrackDto>)
}