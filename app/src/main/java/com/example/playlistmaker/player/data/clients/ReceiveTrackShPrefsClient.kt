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
            trackName = "empty",
            artistName = "empty",
            trackTimeMillis = 0,
            artworkUrl100 = null,
            trackId = 0,
            collectionName = null,
            releaseDate = "empty",
            primaryGenreName = "empty",
            country = "empty",
            previewUrl = "empty"
        )
    }
}