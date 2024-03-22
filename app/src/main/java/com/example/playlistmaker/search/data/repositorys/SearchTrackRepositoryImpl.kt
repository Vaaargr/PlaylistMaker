package com.example.playlistmaker.search.data.repositorys

import com.example.playlistmaker.search.data.clientInterfaces.NetworkClient
import com.example.playlistmaker.search.data.dto.ITunesRequest
import com.example.playlistmaker.search.data.dto.ITunesResponse
import com.example.playlistmaker.search.data.mapper.ResponseMapper
import com.example.playlistmaker.search.domain.api.repositorys.SearchTrackRepository
import com.example.playlistmaker.search.domain.entity.SearchResponse

class SearchTrackRepositoryImpl(val networkClient: NetworkClient): SearchTrackRepository {
    override fun searchTrack(request: String): SearchResponse {
        val response = networkClient.makeRequest(ITunesRequest(request))
        return if (response is ITunesResponse){
            ResponseMapper.map(response)
        } else {
            SearchResponse(resultCode = 400, trackList = emptyList())
        }
    }
}