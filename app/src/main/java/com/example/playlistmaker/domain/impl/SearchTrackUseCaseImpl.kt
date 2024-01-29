package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.api.repositorys.SearchTrackRepository
import com.example.playlistmaker.domain.api.interactors.SearchTrackUseCase
import com.example.playlistmaker.domain.consumer.Consumer
import java.util.concurrent.Executors

class SearchTrackUseCaseImpl(private val searchTrackRepository: SearchTrackRepository) :
    SearchTrackUseCase {
    private val executor = Executors.newSingleThreadExecutor()
    override fun execute(request: String, consumer: Consumer){
        executor.execute {
            consumer.consume(searchTrackRepository.searchTrack(request))
        }
    }
}