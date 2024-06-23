package com.example.playlistmaker.musicLibrary.data.repositorys

import com.example.playlistmaker.musicLibrary.data.database.TrackDatabase
import com.example.playlistmaker.musicLibrary.data.mapper.TracksInPlaylistsEntityMapper
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.SaveTrackInPlaylistRepository
import com.example.playlistmaker.search.domain.entity.Track

class SaveTrackInPlaylistRepositoryImpl(
    private val database: TrackDatabase,
    private val mapper: TracksInPlaylistsEntityMapper
) : SaveTrackInPlaylistRepository {

    override suspend fun saveTrack(track: Track) {
        database.getTracksInPlaylistDao().saveTrack(mapper.trackToEntityMap(track))
    }
}