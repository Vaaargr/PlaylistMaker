package com.example.playlistmaker.search.data.repositorys

import com.example.playlistmaker.search.data.clientInterfaces.NetworkClient
import com.example.playlistmaker.search.data.dto.ITunesRequest
import com.example.playlistmaker.search.data.dto.ITunesResponse
import com.example.playlistmaker.search.data.mapper.ResponseMapper
import com.example.playlistmaker.search.domain.api.repositorys.SearchTrackRepository
import com.example.playlistmaker.search.domain.entity.SearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchTrackRepositoryImpl(val networkClient: NetworkClient): SearchTrackRepository {
    override fun searchTrack(request: String): Flow<SearchResponse> = flow {
        val response = networkClient.makeRequest(ITunesRequest(request))
        if (response is ITunesResponse){
            emit(ResponseMapper.map(response))
        } else {
            emit(SearchResponse(resultCode = 400, trackList = emptyList()))
        }
    }
}