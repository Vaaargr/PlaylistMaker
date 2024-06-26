package com.example.playlistmaker.search.domain.entity

data class Track(
    var id: Long?,
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
    val artworkUrl512: String?
)
