package com.example.playlistmaker.musicLibrary.data.repositorys

import com.example.playlistmaker.musicLibrary.data.database.TrackDatabase
import com.example.playlistmaker.musicLibrary.data.entity.PlaylistTrackEntity
import com.example.playlistmaker.player.domain.api.repositorys.PlayerPlaylistTrackRepository

class PlayerPlaylistTrackRepositoryImpl(private val database: TrackDatabase) :
    PlayerPlaylistTrackRepository {
    private val dao = database.getPlaylistTrackDao()

    override suspend fun checkTrackInAllPlaylists(trackID: Long): Boolean {
        val answer = dao.countTrackInPlaylists(trackID = trackID)
        return answer > 0
    }

    override suspend fun checkTrackInPlaylist(playlistID: Long, trackID: Long): Boolean {
        val answer = dao.checkTrackInPlaylist(playlistID = playlistID, trackID = trackID)
        return answer > 0
    }

    override suspend fun addTrackToPlaylist(playlistID: Long, trackID: Long) {
        dao.saveTrackInPlaylist(
            PlaylistTrackEntity(
                id = null,
                playlistID = playlistID,
                trackID = trackID
            )
        )
    }
}