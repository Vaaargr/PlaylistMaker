package com.example.playlistmaker.search.data.repositorys

import com.example.playlistmaker.search.data.clientInterfaces.SendTrackLocalClient
import com.example.playlistmaker.search.data.mapper.TrackDtoMapper
import com.example.playlistmaker.search.domain.api.repositorys.SendTrackRepository
import com.example.playlistmaker.search.domain.entity.Track

class SendTrackRepositoryImpl(private val client: SendTrackLocalClient): SendTrackRepository {
    override fun sendTrack(track: Track) {
        client.sendTrack(TrackDtoMapper.trackToTrackDtoMap(track))
    }
}