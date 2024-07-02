package com.example.playlistmaker.musicLibrary.domain.api.repositorys

import com.example.playlistmaker.musicLibrary.domain.entity.Playlist
import com.example.playlistmaker.search.domain.entity.Track

interface PlaylistRepository {
    suspend fun getPlaylist(id: Long): Playlist

    suspend fun deletePlaylist(id: Long)

    suspend fun getAllTracksInPlaylist(playlistID: Long): List<Track>

    suspend fun checkTrackInPlaylists(trackID: Long): Boolean

    suspend fun deleteTrackFromPlaylist(playlistID: Long, trackID: Long)

    suspend fun deleteTrack(trackID: Long)

    suspend fun updatePlaylist(playlist: Playlist)
}