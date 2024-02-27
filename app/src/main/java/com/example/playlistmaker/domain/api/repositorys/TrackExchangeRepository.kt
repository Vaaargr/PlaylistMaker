package com.example.playlistmaker.domain.api.repositorys

import com.example.playlistmaker.domain.entity.Track

interface TrackExchangeRepository {
    fun sendTrack(track: Track)
    fun receiveTrack(): Track
}