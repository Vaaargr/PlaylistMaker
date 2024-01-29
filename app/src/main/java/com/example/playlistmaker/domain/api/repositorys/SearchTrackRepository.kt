package com.example.playlistmaker.domain.api.repositorys

import com.example.playlistmaker.domain.entity.SearchResponse

interface SearchTrackRepository {
    fun searchTrack(request: String): SearchResponse
}