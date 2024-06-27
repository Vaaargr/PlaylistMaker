package com.example.playlistmaker.musicLibrary.data.repositorys

import com.example.playlistmaker.musicLibrary.data.database.TrackDatabase
import com.example.playlistmaker.musicLibrary.data.mapper.TrackEntityMapper
import com.example.playlistmaker.player.domain.api.repositorys.PlayerTrackRepository
import com.example.playlistmaker.search.domain.entity.Track

class PlayerTrackRepositoryImpl(private val database: TrackDatabase, private val mapper: TrackEntityMapper): PlayerTrackRepository {
    private val dao = database.getTracksDao()

    override suspend fun loadTrack(trackID: Long): Track {
        return mapper.entityToTrackMap(dao.findTrack(trackID))
    }

    override suspend fun updateTrack(track: Track) {
        dao.updateTrack(mapper.trackToEntityMap(track))
    }

    override suspend fun deleteTrack(trackID: Long) {
        dao.deleteTrack(trackID)
    }
}