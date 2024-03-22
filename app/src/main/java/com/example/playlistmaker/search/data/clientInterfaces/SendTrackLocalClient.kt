package com.example.playlistmaker.search.data.clientInterfaces

import com.example.playlistmaker.search.data.dto.TrackDto

interface SendTrackLocalClient {
    fun sendTrack(trackDto: TrackDto)

}