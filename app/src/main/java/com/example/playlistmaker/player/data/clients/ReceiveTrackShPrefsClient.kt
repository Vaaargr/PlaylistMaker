package com.example.playlistmaker.player.data.clients

import android.content.SharedPreferences
import com.example.playlistmaker.player.data.clientIterfaces.ReceiveTrackLocalClient
import com.example.playlistmaker.search.data.dto.TrackDto
import com.google.gson.Gson

class ReceiveTrackShPrefsClient(private val sharedPreferences: SharedPreferences,private val trackKey: String): ReceiveTrackLocalClient {
    override fun receiveTrack(): TrackDto {
        val json = sharedPreferences.getString(trackKey, "")
        return if (json.isNullOrEmpty()) {
            TRACK_DTO_MOCK
        } else {
            Gson().fromJson(json, TrackDto::class.java)
        }
    }

    companion object {
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