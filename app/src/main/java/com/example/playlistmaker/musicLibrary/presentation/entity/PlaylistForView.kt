package com.example.playlistmaker.musicLibrary.presentation.entity

data class PlaylistForView(
    var id: Long?,
    var name: String,
    var description: String?,
    var imagePath: String?,
    var tracksCount: Int
)
