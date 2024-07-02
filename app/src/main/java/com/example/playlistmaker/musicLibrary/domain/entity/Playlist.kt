package com.example.playlistmaker.musicLibrary.domain.entity

data class Playlist(
    var id: Long?,
    val name: String,
    var description: String?,
    val imagePath: String?,
    var tracksCount: Int
)
