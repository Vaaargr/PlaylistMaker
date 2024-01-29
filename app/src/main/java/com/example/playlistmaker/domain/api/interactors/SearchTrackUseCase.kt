package com.example.playlistmaker.domain.api.interactors

import com.example.playlistmaker.domain.consumer.Consumer

interface SearchTrackUseCase {
    fun execute(request: String, consumer: Consumer)
}