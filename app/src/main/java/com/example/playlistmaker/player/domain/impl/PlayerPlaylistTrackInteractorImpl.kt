package com.example.playlistmaker.player.domain.impl

import com.example.playlistmaker.player.domain.api.interactors.PlayerPlaylistTrackInteractor
import com.example.playlistmaker.player.domain.api.repositorys.PlayerPlaylistTrackRepository

class PlayerPlaylistTrackInteractorImpl(private val playerPlaylistTrackRepository: PlayerPlaylistTrackRepository): PlayerPlaylistTrackInteractor {
    override suspend fun checkTrackInAllPlaylists(trackID: Long): Boolean {
        return playerPlaylistTrackRepository.checkTrackInAllPlaylists(trackID = trackID)
    }

    override suspend fun checkTrackInPlaylist(playlistID: Long, trackID: Long): Boolean {
        return playerPlaylistTrackRepository.checkTrackInPlaylist(playlistID = playlistID, trackID = trackID)
    }

    override suspend fun addTrackToPlaylist(playlistID: Long, trackID: Long, trackPosition: Int) {
        playerPlaylistTrackRepository.addTrackToPlaylist(playlistID = playlistID, trackID = trackID, trackPosition = trackPosition)
    }

    override suspend fun getTrackPosition(playlistID: Long): Int{
        return playerPlaylistTrackRepository.getTrackPosition(playlistID = playlistID)
    }
}