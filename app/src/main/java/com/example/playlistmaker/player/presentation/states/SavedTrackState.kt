package com.example.playlistmaker.player.presentation.states

sealed class SavedTrackState {

    object savedTrack: SavedTrackState()

    object unsavedTrack: SavedTrackState()
}