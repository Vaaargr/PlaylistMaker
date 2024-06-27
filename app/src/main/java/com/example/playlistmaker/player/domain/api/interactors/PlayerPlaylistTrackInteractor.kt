package com.example.playlistmaker.player.domain.api.interactors

interface PlayerPlaylistTrackInteractor {

    suspend fun checkTrackInAllPlaylists(trackID: Long): Boolean

    suspend fun checkTrackInPlaylist(playlistID: Long, trackID: Long): Boolean

    suspend fun addTrackToPlaylist(playlistID: Long, trackID: Long)
}