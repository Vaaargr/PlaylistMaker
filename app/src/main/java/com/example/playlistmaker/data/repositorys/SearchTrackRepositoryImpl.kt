package com.example.playlistmaker.data.repositorys

import com.example.playlistmaker.data.clientIterfaces.NetworkClient
import com.example.playlistmaker.data.dto.ITunesRequest
import com.example.playlistmaker.data.dto.ITunesResponse
import com.example.playlistmaker.data.mapper.ResponseMapper
import com.example.playlistmaker.domain.api.repositorys.SearchTrackRepository
import com.example.playlistmaker.domain.entity.SearchResponse

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