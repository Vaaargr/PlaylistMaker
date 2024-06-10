package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.domain.api.repositorys.SearchTrackRepository
import com.example.playlistmaker.search.domain.api.interactors.SearchTrackUseCase
import com.example.playlistmaker.search.domain.entity.SearchResponse
import kotlinx.coroutines.flow.Flow

class SearchTrackUseCaseImpl(private val searchTrackRepository: SearchTrackRepository) :
    SearchTrackUseCase {

    override fun execute(request: String): Flow<SearchResponse>{
        return searchTrackRepository.searchTrack(request)
    }
}