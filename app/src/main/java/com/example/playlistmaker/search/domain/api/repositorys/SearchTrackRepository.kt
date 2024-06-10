package com.example.playlistmaker.search.domain.api.repositorys

import com.example.playlistmaker.search.domain.entity.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchTrackRepository {
    fun searchTrack(request: String): Flow<SearchResponse>
}