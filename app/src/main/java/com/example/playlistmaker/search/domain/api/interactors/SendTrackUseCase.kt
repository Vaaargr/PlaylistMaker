package com.example.playlistmaker.search.domain.api.interactors

import com.example.playlistmaker.search.domain.entity.Track

interface SendTrackUseCase {
    fun execute(track: Track)
}