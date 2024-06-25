package com.example.playlistmaker.musicLibrary.domain.impl

import com.example.playlistmaker.musicLibrary.domain.api.interactors.ReceiveSavedTracksUseCase
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.TracksLibraryRepository
import com.example.playlistmaker.search.domain.entity.Track
import kotlinx.coroutines.flow.Flow

class ReceiveSavedTracksUseCaseImpl(private val tracksLibraryRepository: TracksLibraryRepository) :
    ReceiveSavedTracksUseCase {
    override fun execute(): Flow<List<Track>> {
        return tracksLibraryRepository.getSavedTracks()
    }
}