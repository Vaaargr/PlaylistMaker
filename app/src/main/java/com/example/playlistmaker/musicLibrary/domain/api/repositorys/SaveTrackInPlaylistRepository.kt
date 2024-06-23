package com.example.playlistmaker.musicLibrary.domain.api.repositorys

import com.example.playlistmaker.search.domain.entity.Track

interface SaveTrackInPlaylistRepository {

    suspend fun saveTrack(track: Track)
}