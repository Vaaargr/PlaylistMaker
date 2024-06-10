package com.example.playlistmaker.search.domain.api.interactors

import com.example.playlistmaker.search.domain.entity.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchTrackUseCase {
    fun execute(request: String): Flow<SearchResponse>
}