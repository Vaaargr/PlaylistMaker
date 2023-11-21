package com.example.playlistmaker

import android.content.SharedPreferences
import com.example.playlistmaker.enums.HistoryDictionary
import com.example.playlistmaker.models.ITunesResponse
import com.example.playlistmaker.models.Track
import com.google.gson.Gson

class SearchHistory(private val sharedPrefs: SharedPreferences) {
    fun getHistory(): ArrayList<Track> {
        val json =
            sharedPrefs.getString(HistoryDictionary.SEARCH_HISTORY.value, null) ?: return ArrayList<Track>()
        return Gson().fromJson(json, ITunesResponse::class.java).results
    }

    fun saveTrack(input: Track) {
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
    }

    fun clearHistory() {
        sharedPrefs.edit().remove(HistoryDictionary.SEARCH_HISTORY.value).apply()
    }

    private fun saveHistory(inputHistory: ArrayList<Track>) {
        sharedPrefs.edit()
            .putString(HistoryDictionary.SEARCH_HISTORY.value, Gson().toJson(ITunesResponse(inputHistory)))
            .apply()
    }
}