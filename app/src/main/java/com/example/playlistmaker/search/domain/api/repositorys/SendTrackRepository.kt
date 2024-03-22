package com.example.playlistmaker.search.domain.api.repositorys

import com.example.playlistmaker.search.domain.entity.Track

interface SendTrackRepository {
    fun sendTrack(track: Track)
}