package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.api.interactors.TrackExchangeInteract
import com.example.playlistmaker.domain.api.repositorys.TrackExchangeRepository
import com.example.playlistmaker.domain.entity.Track

class TrackExchangeInteractImpl(private val trackExchangeRepository: TrackExchangeRepository) :
    TrackExchangeInteract {
    override fun sendTrack(track: Track) {
        trackExchangeRepository.sendTrack(track)
    }

    override fun receiveTrack(): Track {
        return trackExchangeRepository.receiveTrack()
    }
}