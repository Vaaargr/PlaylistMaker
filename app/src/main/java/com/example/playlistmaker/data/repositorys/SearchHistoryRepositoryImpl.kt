package com.example.playlistmaker.data.repositorys

import com.example.playlistmaker.data.clientIterfaces.SearchHistoryClient
import com.example.playlistmaker.data.mapper.TrackDtoMapper
import com.example.playlistmaker.domain.api.repositorys.SearchHistoryRepository
import com.example.playlistmaker.domain.entity.Track

class SearchHistoryRepositoryImpl(private val searchHistoryClient: SearchHistoryClient): SearchHistoryRepository {
    override fun getHistory(): ArrayList<Track> {
        return TrackDtoMapper.trackDtoArrayToTrackArrayMap(searchHistoryClient.getHistory())
    }

    override fun clearHistory() {
        searchHistoryClient.clearHistory()
    }

    override fun saveHistory(inputHistory: ArrayList<Track>) {
        searchHistoryClient.saveHistory(TrackDtoMapper.trackArrayToTrackDtoArrayMap(inputHistory))
    }
}