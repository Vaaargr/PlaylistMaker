package com.example.playlistmaker.search.domain.api.repositorys

import com.example.playlistmaker.search.domain.entity.SearchResponse

interface SearchTrackRepository {
    fun searchTrack(request: String): SearchResponse
}