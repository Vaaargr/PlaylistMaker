package com.example.playlistmaker.player.presentation.states

sealed class SavedTrackState {

    object SavedTrack: SavedTrackState()

    object UnsavedTrack: SavedTrackState()
}