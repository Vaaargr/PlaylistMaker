package com.example.playlistmaker.musicLibrary.presentation.adapter.clickListener

import com.example.playlistmaker.search.presentation.model.TrackForView

interface OnTrackInPlaylistClickListener {
    fun onClick(track: TrackForView)

    fun onLongClick(track: TrackForView)
}