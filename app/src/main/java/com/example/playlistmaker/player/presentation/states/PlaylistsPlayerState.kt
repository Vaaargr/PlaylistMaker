package com.example.playlistmaker.player.presentation.states

import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView

sealed class PlaylistsPlayerState {
    object Empty: PlaylistsPlayerState()
    class Content(val playlists: List<PlaylistForView>):PlaylistsPlayerState()
}