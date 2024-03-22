package com.example.playlistmaker.search.presentation.ui

import com.example.playlistmaker.search.presentation.model.TrackForView

interface TrackClickListener {
    fun clickOnTrack(track: TrackForView)
}