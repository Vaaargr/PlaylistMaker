package com.example.playlistmaker.search.data.mapper

import com.example.playlistmaker.search.data.dto.TrackDto
import com.example.playlistmaker.search.domain.entity.Track

class TrackDtoMapper {
    fun trackDtoToTrackMap(trackDto: TrackDto): Track {
        return Track(
            id = trackDto.id,
            trackName = trackDto.trackName,
            artistName = trackDto.artistName,
            trackTimeMillis = trackDto.trackTimeMillis,
            artworkUrl100 = trackDto.artworkUrl100,
            trackId = trackDto.trackId,
            collectionName = trackDto.collectionName,
            releaseDate = trackDto.releaseDate,
            primaryGenreName = trackDto.primaryGenreName,
            country = trackDto.country,
            previewUrl = trackDto.previewUrl,
            artworkUrl512 = make512Url(trackDto.artworkUrl100)
        )
    }

    fun trackToTrackDtoMap(track: Track): TrackDto {
        return TrackDto(
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
            previewUrl = track.previewUrl
        )
    }

    private fun make512Url(url: String?): String?{
        return url?.replaceAfterLast('/', "512x512bb.jpg")
    }

    fun trackDtoArrayToTrackArrayMap(trackDtoList: ArrayList<TrackDto>): ArrayList<Track>{
        val result = ArrayList<Track>()
        trackDtoList.forEach { result.add(trackDtoToTrackMap(it)) }
        return result
    }

    fun trackArrayToTrackDtoArrayMap(trackList: ArrayList<Track>): ArrayList<TrackDto>{
        val result = ArrayList<TrackDto>()
        trackList.forEach { result.add(trackToTrackDtoMap(it)) }
        return result
    }
}