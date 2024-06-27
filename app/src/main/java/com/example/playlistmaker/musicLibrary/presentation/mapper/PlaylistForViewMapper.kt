package com.example.playlistmaker.musicLibrary.presentation.mapper

import com.example.playlistmaker.musicLibrary.domain.entity.Playlist
import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView

class PlaylistForViewMapper {

    fun playlistForViewToPlaylistMap(playlistForView: PlaylistForView): Playlist {
        return Playlist(
            id = playlistForView.id,
            name = playlistForView.name,
            description = playlistForView.description,
            imagePath = playlistForView.imagePath,
            tracksCount = playlistForView.tracksCount
        )
    }

    fun playlistToPlaylistForView(playlist: Playlist): PlaylistForView {
        return PlaylistForView(
            id = playlist.id,
            name = playlist.name,
            description = playlist.description,
            imagePath = playlist.imagePath,
            tracksCount = playlist.tracksCount
        )
    }

}