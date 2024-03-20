package com.example.playlistmaker.player.domain.api.repositorys

import com.example.playlistmaker.search.domain.entity.Track

interface ReceiveTrackUseCaseRepository {

    fun receiveTrack(): Track
}