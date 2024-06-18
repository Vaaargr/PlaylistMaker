package com.example.playlistmaker.musicLibrary.domain.api.repositorys

import com.example.playlistmaker.search.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface TracksLibraryRepository {
    fun getSavedTracks(): Flow<List<Track>>
}