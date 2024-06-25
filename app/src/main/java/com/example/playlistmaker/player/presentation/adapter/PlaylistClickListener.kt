package com.example.playlistmaker.player.presentation.adapter

import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView

interface PlaylistClickListener {
    fun clickOnPlaylist(playlist: PlaylistForView)
}