package com.example.playlistmaker.musicLibrary.presentation.adapter.clickListener

import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView

interface PlaylistClickListener {
    fun clickOnPlaylist(playlist: PlaylistForView)
}