package com.example.playlistmaker.search.domain.api.interactors

import com.example.playlistmaker.search.domain.consumer.Consumer

interface SearchTrackUseCase {
    fun execute(request: String, consumer: Consumer)
}