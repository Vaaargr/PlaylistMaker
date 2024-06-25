package com.example.playlistmaker.musicLibrary.presentation.mapper

import com.example.playlistmaker.musicLibrary.domain.entity.Playlist
import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView
import com.example.playlistmaker.search.presentation.mappers.TrackViewMapper

class PlaylistForViewMapper() {

    fun playlistForViewToPlaylistMap(playlistForView: PlaylistForView): Playlist{
        return Playlist(
            id = playlistForView.id,
            name = playlistForView.name,
            description = playlistForView.description,
            imagePath = playlistForView.imagePath,
            tracksIDList =playlistForView.tracksIDList,
            tracksCount = playlistForView.tracksCount
        )
    }

    fun playlistToPlaylistForView(playlist: Playlist): PlaylistForView{
        return PlaylistForView(
            id = playlist.id,
            name = playlist.name,
            description = playlist.description,
            imagePath = playlist.imagePath,
            tracksIDList = playlist.tracksIDList,
            tracksCount = playlist.tracksCount
        )
    }

}