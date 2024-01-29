package com.example.playlistmaker.data.clients.sharedPrefs

import android.content.SharedPreferences
import com.example.playlistmaker.data.clientIterfaces.SearchHistoryClient
import com.example.playlistmaker.data.dto.ITunesResponse
import com.example.playlistmaker.data.dto.TrackDto
import com.google.gson.Gson

class SharedPrefsSearchHistoryClient(private val sharedPreferences: SharedPreferences) :
    SearchHistoryClient {
    override fun getHistory(): ArrayList<TrackDto> {
        val json =
            sharedPreferences.getString(SEARCH_HISTORY, null)
                ?: return ArrayList<TrackDto>()
        return Gson().fromJson(json, ITunesResponse::class.java).results
    }

    override fun clearHistory() {
        sharedPreferences.edit().remove(SEARCH_HISTORY).apply()
    }

    override fun saveHistory(inputHistory: ArrayList<TrackDto>) {
        sharedPreferences.edit()
            .putString(SEARCH_HISTORY, Gson().toJson(ITunesResponse(inputHistory)))
            .apply()
    }

    companion object {
        const val SEARCH_HISTORY = "search history"
    }
}