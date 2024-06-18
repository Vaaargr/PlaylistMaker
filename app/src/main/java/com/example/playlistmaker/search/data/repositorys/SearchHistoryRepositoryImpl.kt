package com.example.playlistmaker.search.data.repositorys

import com.example.playlistmaker.search.data.clientInterfaces.SearchHistoryClient
import com.example.playlistmaker.search.data.mapper.TrackDtoMapper
import com.example.playlistmaker.search.domain.api.repositorys.SearchHistoryRepository
import com.example.playlistmaker.search.domain.entity.Track

class SearchHistoryRepositoryImpl(
    private val searchHistoryClient: SearchHistoryClient,
    private val trackMapper: TrackDtoMapper
) :
    SearchHistoryRepository {
    override fun getHistory(): ArrayList<Track> {
        return trackMapper.trackDtoArrayToTrackArrayMap(searchHistoryClient.getHistory())
    }

    override fun clearHistory() {
        searchHistoryClient.clearHistory()
    }

    override fun saveHistory(inputHistory: ArrayList<Track>) {
        searchHistoryClient.saveHistory(trackMapper.trackArrayToTrackDtoArrayMap(inputHistory))
    }
}