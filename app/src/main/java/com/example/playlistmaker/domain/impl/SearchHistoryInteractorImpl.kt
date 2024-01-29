package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.api.interactors.SearchHistoryInteractor
import com.example.playlistmaker.domain.api.repositorys.SearchHistoryRepository
import com.example.playlistmaker.domain.entity.Track

class SearchHistoryInteractorImpl(private val searchHistoryRepository: SearchHistoryRepository): SearchHistoryInteractor {
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

    /*fun getHistory(): ArrayList<Track> {
        val json =
            sharedPrefs.getString(HistoryDictionary.SEARCH_HISTORY.value, null) ?: return ArrayList<Track>()
        return Gson().fromJson(json, ITunesResponse::class.java).results
    }*/

    /*fun saveTrack(input: Track) {
        val history = getHistory()
        if (history.isEmpty()) {
            history.add(input)
            saveHistory(history)
        } else {
            if (history.contains(input)) {
                history.remove(input)
                history.add(0, input)
            } else {
                history.add(0,input)
            }
            while (history.size > 10) {
                history.removeLast()
            }
            saveHistory(history)
        }
    }*/

    /*fun clearHistory() {
        sharedPrefs.edit().remove(HistoryDictionary.SEARCH_HISTORY.value).apply()
    }*/

    private fun saveHistory(inputHistory: ArrayList<Track>) {
        searchHistoryRepository.saveHistory(inputHistory)
        /*sharedPrefs.edit()
            .putString(HistoryDictionary.SEARCH_HISTORY.value, Gson().toJson(ITunesResponse(inputHistory)))
            .apply()*/
    }
}