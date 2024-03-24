package com.example.playlistmaker.search.data.clients.sharedPrefs

import android.content.SharedPreferences
import com.example.playlistmaker.search.data.clientInterfaces.SearchHistoryClient
import com.example.playlistmaker.search.data.dto.ITunesResponse
import com.example.playlistmaker.search.data.dto.TrackDto
import com.google.gson.Gson

class SearchHistoryShPrefsClient(
    private val sharedPreferences: SharedPreferences,
    private val historyKey: String,
    private val gson: Gson
) :
    SearchHistoryClient {
    override fun getHistory(): ArrayList<TrackDto> {
        val json =
            sharedPreferences.getString(historyKey, null)
                ?: return ArrayList<TrackDto>()
        return gson.fromJson(json, ITunesResponse::class.java).results
    }

    override fun clearHistory() {
        sharedPreferences.edit().remove(historyKey).apply()
    }

    override fun saveHistory(inputHistory: ArrayList<TrackDto>) {
        sharedPreferences.edit()
            .putString(historyKey, gson.toJson(ITunesResponse(inputHistory)))
            .apply()
    }
}