package com.example.playlistmaker.presentation.ui.search

import com.example.playlistmaker.presentation.model.TrackForView

interface TrackClickListener {
    fun clickOnTrack(track: TrackForView)
}