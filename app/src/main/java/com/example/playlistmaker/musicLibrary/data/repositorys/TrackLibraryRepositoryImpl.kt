package com.example.playlistmaker.musicLibrary.data.repositorys

import com.example.playlistmaker.musicLibrary.data.database.TrackDatabase
import com.example.playlistmaker.musicLibrary.data.entity.TrackEntity
import com.example.playlistmaker.musicLibrary.data.mapper.TrackEntityMapper
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.TracksLibraryRepository
import com.example.playlistmaker.search.domain.entity.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrackLibraryRepositoryImpl(private val database: TrackDatabase, private val mapper: TrackEntityMapper): TracksLibraryRepository {
    override  fun getLikedTracks(): Flow<List<Track>> = flow {
        val tracks = database.getTracksDao().getAllLikedTracks()
        emit(listEntityToListTrack(tracks))
    }

    private fun listEntityToListTrack(entityList: List<TrackEntity>): List<Track>{
        return entityList.map { mapper.entityToTrackMap(it) }
    }
}