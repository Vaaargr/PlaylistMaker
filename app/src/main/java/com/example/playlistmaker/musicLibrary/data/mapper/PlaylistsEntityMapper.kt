package com.example.playlistmaker.musicLibrary.data.mapper

import com.example.playlistmaker.musicLibrary.data.entity.PlaylistEntity
import com.example.playlistmaker.musicLibrary.domain.entity.Playlist
import com.google.gson.Gson

class PlaylistsEntityMapper(private val gson: Gson) {

    fun entityToPlaylistMap(entity: PlaylistEntity): Playlist {

        return Playlist(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            imagePath = entity.imagePath,
            tracksCount = entity.tracksCount,
        )
    }

    fun playlistToEntityMap(playlist: Playlist): PlaylistEntity {


        return PlaylistEntity(
            id = playlist.id,
            name = playlist.name,
            description = playlist.description,
            imagePath = playlist.imagePath,
            tracksCount = playlist.tracksCount,
        )
    }
}
