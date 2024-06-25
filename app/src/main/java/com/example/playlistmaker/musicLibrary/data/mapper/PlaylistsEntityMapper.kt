package com.example.playlistmaker.musicLibrary.data.mapper

import com.example.playlistmaker.musicLibrary.data.entity.PlaylistEntity
import com.example.playlistmaker.musicLibrary.data.entity.SavedTracks
import com.example.playlistmaker.musicLibrary.domain.entity.Playlist
import com.google.gson.Gson

class PlaylistsEntityMapper(private val gson: Gson) {

    fun entityToPlaylistMap(entity: PlaylistEntity): Playlist {
        val trackList = gson.fromJson(entity.tracksIDList, SavedTracks::class.java)

        return Playlist(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            imagePath = entity.imagePath,
            tracksCount = entity.tracksCount,
            tracksIDList = trackList.tracks
        )
    }

    fun playlistToEntityMap(playlist: Playlist): PlaylistEntity {


        return PlaylistEntity(
            id = playlist.id,
            name = playlist.name,
            description = playlist.description,
            imagePath = playlist.imagePath,
            tracksCount = playlist.tracksCount,
            tracksIDList = gson.toJson(SavedTracks(playlist.tracksIDList))
        )
    }
}
