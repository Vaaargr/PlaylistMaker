package com.example.playlistmaker.musicLibrary.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("playlist_track")
data class PlaylistTrackEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    val playlistID: Long,
    val trackID: Long,
    val position: Int
)