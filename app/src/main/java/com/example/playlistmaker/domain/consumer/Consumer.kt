package com.example.playlistmaker.domain.consumer

import com.example.playlistmaker.domain.entity.SearchResponse

interface Consumer {
    fun consume(searchResponse: SearchResponse)
}