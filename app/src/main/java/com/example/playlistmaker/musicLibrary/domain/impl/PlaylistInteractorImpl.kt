package com.example.playlistmaker.musicLibrary.domain.impl

import com.example.playlistmaker.musicLibrary.domain.api.interactors.PlaylistInteractor
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.PlaylistRepository
import com.example.playlistmaker.musicLibrary.domain.entity.Playlist
import com.example.playlistmaker.search.domain.entity.Track

class PlaylistInteractorImpl(private val repository: PlaylistRepository): PlaylistInteractor {

    override suspend fun getPlaylist(id: Long): Playlist {
        return repository.getPlaylist(id = id)
    }

    override suspend fun deletePlaylist(id: Long) {
        repository.deletePlaylist(id = id)
    }

    override suspend fun getAllTracksInPlaylist(playlistID: Long): List<Track> {
        return repository.getAllTracksInPlaylist(playlistID = playlistID)
    }

    override suspend fun checkTrackInPlaylists(trackID: Long): Boolean {
        return repository.checkTrackInPlaylists(trackID = trackID)
    }

    override suspend fun deleteTrackFromPlaylist(playlistID: Long, trackID: Long) {
        repository.deleteTrackFromPlaylist(playlistID = playlistID, trackID = trackID)
    }

    override suspend fun deleteTrack(trackID: Long) {
        repository.deleteTrack(trackID = trackID)
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        repository.updatePlaylist(playlist = playlist)
    }
}