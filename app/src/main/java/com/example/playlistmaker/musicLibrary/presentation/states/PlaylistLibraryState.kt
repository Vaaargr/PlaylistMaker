package com.example.playlistmaker.musicLibrary.presentation.states

import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView

sealed class PlaylistLibraryState {

    object Empty: PlaylistLibraryState()
    class Content(val content: List<PlaylistForView>):PlaylistLibraryState()
}