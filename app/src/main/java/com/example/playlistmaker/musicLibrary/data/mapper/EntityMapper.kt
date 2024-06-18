package com.example.playlistmaker.musicLibrary.data.mapper

import com.example.playlistmaker.musicLibrary.data.entity.TrackEntity
import com.example.playlistmaker.search.domain.entity.Track

class EntityMapper {

    fun entityToTrackMap(entity: TrackEntity): Track {
        return Track(
            id = entity.id,
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

    fun trackToEntityMap(track: Track): TrackEntity {
        return TrackEntity(
            id = track.id,
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