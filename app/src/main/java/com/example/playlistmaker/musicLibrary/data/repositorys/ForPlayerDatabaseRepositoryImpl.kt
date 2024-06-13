package com.example.playlistmaker.musicLibrary.data.repositorys

import com.example.playlistmaker.musicLibrary.data.database.TrackDatabase
import com.example.playlistmaker.musicLibrary.data.mapper.EntityMapper
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.ForPlayerDatabaseRepository
import com.example.playlistmaker.search.domain.entity.Track

class ForPlayerDatabaseRepositoryImpl(
    private val database: TrackDatabase,
    private val mapper: EntityMapper
) :
    ForPlayerDatabaseRepository {
    override suspend fun saveTrack(track: Track) {
        database.getDao().saveTrack(mapper.trackToEntityMap(track))
    }

    override suspend fun deleteTrack(trackID: Int) {
        database.getDao().deleteTrack(trackID)
    }

    override suspend fun checkTrack(trackID: Int): Boolean {
        return database.getDao().findTrack(trackID).isNotEmpty()
    }
}