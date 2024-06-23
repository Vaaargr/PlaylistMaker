package com.example.playlistmaker.musicLibrary.presentation.entity

data class PlaylistForView(
    var id: Long?,
    val name: String,
    var description: String?,
    val imagePath: String?,
    var tracksIDList: List<Int>,
    var tracksCount: Int
)
