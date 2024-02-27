package com.example.playlistmaker.data.repositorys

import com.example.playlistmaker.data.clientIterfaces.LocalClient
import com.example.playlistmaker.data.mapper.TrackDtoMapper
import com.example.playlistmaker.domain.api.repositorys.TrackExchangeRepository
import com.example.playlistmaker.domain.entity.Track

class TrackExchangeRepositoryImpl(private val localClient: LocalClient): TrackExchangeRepository {
    override fun sendTrack(track: Track){
        localClient.saveTrackToSend(TrackDtoMapper.trackToTrackDtoMap(track))
    }

    override fun receiveTrack(): Track{
        return TrackDtoMapper.trackDtoToTrackMap(localClient.loadTrackToSend())
    }
}