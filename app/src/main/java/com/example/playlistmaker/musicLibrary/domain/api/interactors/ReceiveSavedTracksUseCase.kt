package com.example.playlistmaker.musicLibrary.domain.api.interactors

import com.example.playlistmaker.search.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface ReceiveSavedTracksUseCase {
    suspend fun execute(): Flow<List<Track>>
}