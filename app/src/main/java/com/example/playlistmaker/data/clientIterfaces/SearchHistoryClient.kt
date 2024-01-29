package com.example.playlistmaker.data.clientIterfaces

import com.example.playlistmaker.data.dto.TrackDto

interface SearchHistoryClient {
    fun getHistory(): ArrayList<TrackDto>

     fun clearHistory()

     fun saveHistory(inputHistory: ArrayList<TrackDto>)
}