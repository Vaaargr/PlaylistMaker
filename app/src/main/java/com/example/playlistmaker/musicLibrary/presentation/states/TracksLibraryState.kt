package com.example.playlistmaker.musicLibrary.presentation.states

import com.example.playlistmaker.search.presentation.model.TrackForView

sealed class TracksLibraryState {
    object Empty : TracksLibraryState()

    class Content(val content: List<TrackForView>): TracksLibraryState()
}