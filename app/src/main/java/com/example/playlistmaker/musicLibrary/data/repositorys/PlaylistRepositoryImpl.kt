package com.example.playlistmaker.musicLibrary.data.repositorys

import com.example.playlistmaker.musicLibrary.data.database.TrackDatabase
import com.example.playlistmaker.musicLibrary.data.mapper.PlaylistsEntityMapper
import com.example.playlistmaker.musicLibrary.data.mapper.TrackEntityMapper
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.PlaylistRepository
import com.example.playlistmaker.musicLibrary.domain.entity.Playlist
import com.example.playlistmaker.search.domain.entity.Track

class PlaylistRepositoryImpl(
    private val database: TrackDatabase,
    private val playlistMapper: PlaylistsEntityMapper,
    private val trackMapper: TrackEntityMapper
) : PlaylistRepository {
    override suspend fun getPlaylist(id: Long): Playlist {
        return playlistMapper.entityToPlaylistMap(database.getPlaylistsDao().loadPlaylist(id = id))
    }

    override suspend fun deletePlaylist(id: Long) {
        database.getPlaylistsDao().deletePlaylist(id = id)
    }

    override suspend fun getAllTracksInPlaylist(playlistID: Long): List<Track> {
        val answer = database.getPlaylistTrackDao().getAllTracksInPlaylist(playlistID = playlistID)
        return answer.map { trackMapper.entityToTrackMap(it) }
    }

    override suspend fun checkTrackInPlaylists(trackID: Long): Boolean {
        val answer = database.getPlaylistTrackDao().countTrackInPlaylists(trackID = trackID)
        return answer > 0
    }

    override suspend fun deleteTrackFromPlaylist(playlistID: Long, trackID: Long) {
        database.getPlaylistTrackDao()
            .deleteTrackFromPlaylist(playlistID = playlistID, trackID = trackID)

        if (database.getPlaylistTrackDao().checkTrackInAllPlaylists(trackID).isEmpty()) {
            deleteTrack(trackID = trackID)
        }
    }

    override suspend fun deleteTrack(trackID: Long) {
        if (!database.getTracksDao().findTrack(trackID = trackID).isLicked) {
            database.getTracksDao().deleteTrack(trackID = trackID)
        }
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        database.getPlaylistsDao()
            .updatePlaylist(playlistMapper.playlistToEntityMap(playlist = playlist))
    }
}