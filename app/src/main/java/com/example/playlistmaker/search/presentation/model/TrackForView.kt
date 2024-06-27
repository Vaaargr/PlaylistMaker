package com.example.playlistmaker.search.presentation.model

data class TrackForView(
    var innerID: Long?,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String?,
    val trackId: Long,
    val collectionName: String?,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String,
    val artworkUrl512: String?,
    val trackTimeString: String,
    var isLiked: Boolean
)
