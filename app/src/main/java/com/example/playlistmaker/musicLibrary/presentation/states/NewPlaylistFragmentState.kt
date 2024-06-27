package com.example.playlistmaker.musicLibrary.presentation.states

sealed class NewPlaylistFragmentState(val isEnable: Boolean) {
    object Enable: NewPlaylistFragmentState(true)
    object Disable: NewPlaylistFragmentState(false)

}