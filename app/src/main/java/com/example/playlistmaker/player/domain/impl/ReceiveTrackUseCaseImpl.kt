package com.example.playlistmaker.player.domain.impl

import com.example.playlistmaker.player.domain.api.interactors.ReceiveTrackUseCase
import com.example.playlistmaker.player.domain.api.repositorys.ReceiveTrackUseCaseRepository
import com.example.playlistmaker.search.domain.entity.Track

class ReceiveTrackUseCaseImpl(private val receiveTrackUseCaseRepository: ReceiveTrackUseCaseRepository) :
    ReceiveTrackUseCase {

    override fun execute(): Track {
        return receiveTrackUseCaseRepository.receiveTrack()
    }
}