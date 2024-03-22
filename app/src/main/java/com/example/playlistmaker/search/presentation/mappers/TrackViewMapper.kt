package com.example.playlistmaker.search.presentation.mappers

import com.example.playlistmaker.search.domain.entity.Track
import com.example.playlistmaker.search.presentation.model.TrackForView
import java.text.SimpleDateFormat
import java.util.Locale

object TrackViewMapper {
    fun trackToTrackForViewMap(track: Track): TrackForView {
        return TrackForView(
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
            artworkUrl512 = track.artworkUrl512,
            trackTimeString = SimpleDateFormat(
                "mm:ss",
                Locale.getDefault()
            ).format(track.trackTimeMillis)
        )
    }

    fun trackForViewToTrackMap(trackForView: TrackForView): Track {
        return Track(
            trackName = trackForView.trackName,
            artistName = trackForView.artistName,
            trackTimeMillis = trackForView.trackTimeMillis,
            artworkUrl100 = trackForView.artworkUrl100,
            trackId = trackForView.trackId,
            collectionName = trackForView.collectionName,
            releaseDate = trackForView.releaseDate,
            primaryGenreName = trackForView.primaryGenreName,
            country = trackForView.country,
            previewUrl = trackForView.previewUrl,
            artworkUrl512 = trackForView.artworkUrl512
        )
    }
}