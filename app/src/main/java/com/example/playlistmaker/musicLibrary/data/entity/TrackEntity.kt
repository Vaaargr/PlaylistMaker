package com.example.playlistmaker.musicLibrary.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_track_table")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
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
    var isLicked: Boolean
)