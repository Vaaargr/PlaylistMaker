package com.example.playlistmaker.musicLibrary.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    val name: String,
    var description: String?,
    val imagePath: String?,
    var tracksCount: Int
)