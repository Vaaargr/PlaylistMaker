package com.example.playlistmaker.presentation.model

data class TrackForView(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String?,
    val trackId: Int,
    val collectionName: String?,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String,
    val artworkUrl512: String?,
    val trackTimeString: String
)
