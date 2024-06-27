package com.example.playlistmaker.player.domain.api.repositorys

interface PlayerPlaylistTrackRepository {

    suspend fun checkTrackInAllPlaylists(trackID: Long): Boolean

    suspend fun checkTrackInPlaylist(playlistID: Long, trackID: Long): Boolean

    suspend fun addTrackToPlaylist(playlistID: Long, trackID: Long)
}