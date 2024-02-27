package com.example.playlistmaker.presentation.mappers

import com.example.playlistmaker.domain.entity.Track
import com.example.playlistmaker.presentation.model.TrackForView
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

    fun trackArrayToTrackToViewArrayMap(trackList: ArrayList<Track>): ArrayList<TrackForView> {
        val result = ArrayList<TrackForView>()
        trackList.forEach { result.add(trackToTrackForViewMap(it)) }
        return result
    }

    fun trackForViewArrayToTrackArrayMap(trackForViewList: ArrayList<TrackForView>): ArrayList<Track> {
        val result = ArrayList<Track>()
        trackForViewList.forEach { result.add(trackForViewToTrackMap(it)) }
        return result
    }
}