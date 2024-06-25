package com.example.playlistmaker.player.presentation.states

sealed class SaveTrackInPlaylistState {
    class Success(val playlistName: String): SaveTrackInPlaylistState()
    class Failure(val playlistName: String): SaveTrackInPlaylistState()
}