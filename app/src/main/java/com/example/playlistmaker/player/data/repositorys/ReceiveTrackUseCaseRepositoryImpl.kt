package com.example.playlistmaker.player.data.repositorys

import com.example.playlistmaker.player.data.clientIterfaces.ReceiveTrackLocalClient
import com.example.playlistmaker.search.data.mapper.TrackDtoMapper
import com.example.playlistmaker.player.domain.api.repositorys.ReceiveTrackUseCaseRepository
import com.example.playlistmaker.search.domain.entity.Track

class ReceiveTrackUseCaseRepositoryImpl(
    private val localClient: ReceiveTrackLocalClient,
    private val trackMapper: TrackDtoMapper
) : ReceiveTrackUseCaseRepository {

    override fun receiveTrack(): Track {
        return trackMapper.trackDtoToTrackMap(localClient.receiveTrack())
    }
}