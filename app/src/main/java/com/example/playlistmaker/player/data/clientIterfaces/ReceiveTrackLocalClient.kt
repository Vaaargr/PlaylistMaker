package com.example.playlistmaker.player.data.clientIterfaces

import com.example.playlistmaker.search.data.dto.TrackDto

interface ReceiveTrackLocalClient {
    fun receiveTrack(): TrackDto
}