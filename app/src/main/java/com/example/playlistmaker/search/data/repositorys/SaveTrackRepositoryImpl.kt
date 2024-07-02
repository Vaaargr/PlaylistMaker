package com.example.playlistmaker.search.data.repositorys

import com.example.playlistmaker.musicLibrary.data.database.TrackDatabase
import com.example.playlistmaker.musicLibrary.data.mapper.TrackEntityMapper
import com.example.playlistmaker.search.data.clientInterfaces.SendTrackLocalClient
import com.example.playlistmaker.search.data.mapper.TrackDtoMapper
import com.example.playlistmaker.search.domain.api.repositorys.SaveTrackRepository
import com.example.playlistmaker.search.domain.entity.Track

class SaveTrackRepositoryImpl(private val database: TrackDatabase, private val mapper: TrackEntityMapper): SaveTrackRepository {

    override suspend fun saveTrack(track: Track) {
        database.getTracksDao().saveTrack(mapper.trackToEntityMap(track))
    }
}