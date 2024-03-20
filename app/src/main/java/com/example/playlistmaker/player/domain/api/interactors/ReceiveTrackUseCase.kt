package com.example.playlistmaker.player.domain.api.interactors

import com.example.playlistmaker.search.domain.entity.Track

interface ReceiveTrackUseCase {
    fun execute(): Track
}