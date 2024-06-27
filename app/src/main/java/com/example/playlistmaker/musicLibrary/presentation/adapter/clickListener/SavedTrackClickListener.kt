package com.example.playlistmaker.musicLibrary.presentation.adapter.clickListener

import com.example.playlistmaker.search.presentation.model.TrackForView

interface SavedTrackClickListener {
    fun clickOnTrack(track: TrackForView)
}