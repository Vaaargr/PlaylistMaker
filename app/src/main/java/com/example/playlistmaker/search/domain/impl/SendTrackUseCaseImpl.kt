package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.domain.entity.Track
import com.example.playlistmaker.search.domain.api.interactors.SendTrackUseCase
import com.example.playlistmaker.search.domain.api.repositorys.SendTrackRepository

class SendTrackUseCaseImpl(private val repository: SendTrackRepository): SendTrackUseCase {
    override fun execute(track: Track) {
        repository.sendTrack(track)
    }
}