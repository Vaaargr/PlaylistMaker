package com.example.playlistmaker.search.data.repositorys

import com.example.playlistmaker.search.data.clientInterfaces.NetworkClient
import com.example.playlistmaker.search.data.dto.ITunesRequest
import com.example.playlistmaker.search.data.dto.ITunesResponse
import com.example.playlistmaker.search.data.mapper.ResponseMapper
import com.example.playlistmaker.search.domain.api.repositorys.SearchTrackRepository
import com.example.playlistmaker.search.domain.entity.SearchResponse
import com.example.playlistmaker.search.presentation.mappers.SearchResponseMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchTrackRepositoryImpl(
    private val networkClient: NetworkClient,
    private val responseMapper: ResponseMapper
) : SearchTrackRepository {
    override fun searchTrack(request: String): Flow<SearchResponse> = flow {
        val response = networkClient.makeRequest(ITunesRequest(request))
        if (response is ITunesResponse) {
            emit(responseMapper.map(response))
        } else {
            emit(SearchResponse(resultCode = 400, trackList = emptyList()))
        }
    }
}