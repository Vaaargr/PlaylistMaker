package com.example.playlistmaker.musicLibrary.presentation.states

sealed class NewPlaylistFragmentState(val isEnable: Boolean) {
    object enable: NewPlaylistFragmentState(true)
    object disable: NewPlaylistFragmentState(false)

}