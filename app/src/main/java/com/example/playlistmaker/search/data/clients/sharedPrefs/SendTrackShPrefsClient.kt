package com.example.playlistmaker.search.data.clients.sharedPrefs

import android.content.SharedPreferences
import com.example.playlistmaker.search.data.clientInterfaces.SendTrackLocalClient
import com.example.playlistmaker.search.data.dto.TrackDto
import com.google.gson.Gson

class SendTrackShPrefsClient(private val sharedPreferences: SharedPreferences, private val trackKey: String, private val gson: Gson) : SendTrackLocalClient {
    override fun sendTrack(trackDto: TrackDto) {
        sharedPreferences.edit()
            .putString(trackKey, gson.toJson(trackDto)).apply()
    }
}