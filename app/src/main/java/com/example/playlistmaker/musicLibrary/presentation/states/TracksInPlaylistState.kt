package com.example.playlistmaker.musicLibrary.presentation.states

import com.example.playlistmaker.search.presentation.model.TrackForView

sealed class TracksInPlaylistState {
    object Empty: TracksInPlaylistState()
    class Content(val tracks: List<TrackForView>): TracksInPlaylistState()
}