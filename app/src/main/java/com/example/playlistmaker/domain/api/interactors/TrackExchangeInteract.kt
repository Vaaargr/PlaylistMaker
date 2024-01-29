package com.example.playlistmaker.domain.api.interactors

import com.example.playlistmaker.domain.entity.Track

interface TrackExchangeInteract {
    fun sendTrack(track: Track)

    fun receiveTrack(): Track
}