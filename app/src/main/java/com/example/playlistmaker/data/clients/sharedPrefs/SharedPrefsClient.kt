package com.example.playlistmaker.data.clients.sharedPrefs

import android.content.SharedPreferences
import com.example.playlistmaker.data.clientIterfaces.LocalClient
import com.example.playlistmaker.data.dto.TrackDto
import com.google.gson.Gson

class SharedPrefsClient(val sharedPreferences: SharedPreferences): LocalClient {
    override fun saveTrackToSend(trackDto: TrackDto) {
        sharedPreferences.edit().putString(SEND_TRACK_NAME, Gson().toJson(trackDto)).apply()
    }

    override fun loadTrackToSend(): TrackDto {
        val json = sharedPreferences.getString(SEND_TRACK_NAME, "")
        return if (json.isNullOrEmpty()) {
            TRACK_DTO_MOCK
        } else {
            Gson().fromJson(json, TrackDto::class.java)
        }
    }

    companion object {
        const val SEND_TRACK_NAME = "send track name"
        val TRACK_DTO_MOCK = TrackDto(
            "empty",
            "empty",
            0,
            null,
            0,
            null,
            "empty",
            "empty",
            "empty",
            "empty"
        )
    }
}