package com.example.playlistmaker.musicLibrary.domain.impl

import com.example.playlistmaker.musicLibrary.domain.api.interactors.GetLikedTracksUseCase
import com.example.playlistmaker.musicLibrary.domain.api.repositorys.TracksLibraryRepository
import com.example.playlistmaker.search.domain.entity.Track
import kotlinx.coroutines.flow.Flow

class GetLikedTracksUseCaseImpl(private val tracksLibraryRepository: TracksLibraryRepository) :
    GetLikedTracksUseCase {
    override fun execute(): Flow<List<Track>> {
        return tracksLibraryRepository.getLikedTracks()
    }
}