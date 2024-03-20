package com.example.playlistmaker.search.domain.consumer

import com.example.playlistmaker.search.domain.entity.SearchResponse

interface Consumer {
    fun consume(searchResponse: SearchResponse)
}