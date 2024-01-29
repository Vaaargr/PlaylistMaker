package com.example.playlistmaker.data.clientIterfaces

import com.example.playlistmaker.data.dto.TrackDto

interface LocalClient {
    fun saveTrackToSend(trackDto: TrackDto)
    fun loadTrackToSend(): TrackDto
}