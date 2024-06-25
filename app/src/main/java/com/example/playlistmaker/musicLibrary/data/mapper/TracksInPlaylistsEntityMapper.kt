package com.example.playlistmaker.musicLibrary.data.mapper

import com.example.playlistmaker.musicLibrary.data.entity.TracksInPlaylistsEntity
import com.example.playlistmaker.search.domain.entity.Track

class TracksInPlaylistsEntityMapper {

    fun entityToTrackMap(entity: TracksInPlaylistsEntity): Track {
        return Track(
            id = null,
            trackName = entity.trackName,
            artistName = entity.artistName,
            trackTimeMillis = entity.trackTimeMillis,
            artworkUrl100 = entity.artworkUrl100,
            trackId = entity.trackId,
            collectionName = entity.collectionName,
            releaseDate = entity.releaseDate,
            primaryGenreName = entity.primaryGenreName,
            country = entity.country,
            previewUrl = entity.previewUrl,
            artworkUrl512 = make512Url(entity.artworkUrl100)
        )
    }

    fun trackToEntityMap(track: Track): TracksInPlaylistsEntity {
        return TracksInPlaylistsEntity(
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            trackId = track.trackId,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl,
        )
    }

    private fun make512Url(url: String?): String? {
        return url?.replaceAfterLast('/', "512x512bb.jpg")
    }
}