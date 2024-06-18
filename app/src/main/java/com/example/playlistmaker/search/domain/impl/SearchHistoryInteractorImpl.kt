package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.domain.api.interactors.SearchHistoryInteractor
import com.example.playlistmaker.search.domain.api.repositorys.SearchHistoryRepository
import com.example.playlistmaker.search.domain.entity.Track

class SearchHistoryInteractorImpl(private val searchHistoryRepository: SearchHistoryRepository):
    SearchHistoryInteractor {
    override fun getHistory(): ArrayList<Track> {
       return searchHistoryRepository.getHistory()
    }

    override fun saveTrack(track: Track) {
        val history = getHistory()
        if (history.isEmpty()) {
            history.add(track)
            saveHistory(history)
        } else {
            if (history.contains(track)) {
                history.remove(track)
                history.add(0, track)
            } else {
                history.add(0,track)
            }
            while (history.size > 10) {
                history.removeLast()
            }
            saveHistory(history)
        }
    }

    override fun clearHistory() {
        searchHistoryRepository.clearHistory()
    }

    private fun saveHistory(inputHistory: ArrayList<Track>) {
        searchHistoryRepository.saveHistory(inputHistory)
    }
}