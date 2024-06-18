package com.example.playlistmaker.player.presentation.states

sealed class PlayerState(val buttonState: Boolean) {
    class DEFAULT : PlayerState(false)
    class PREPARED : PlayerState(false)
    class PLAYING : PlayerState(true)
    class PAUSED : PlayerState(false)
}