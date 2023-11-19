package com.example.playlistmaker.models

data class Track(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String,
    val trackId: Int
){
   /* override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Track

        if (trackId != other.trackId) return false

        return true
    }

    override fun hashCode(): Int {
        return trackId
    }*/
}