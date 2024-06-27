package com.example.playlistmaker.musicLibrary.presentation.states

import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView

sealed class EditPlaylistState {
    object New: EditPlaylistState()
    class Edit(val playlist: PlaylistForView): EditPlaylistState()
}