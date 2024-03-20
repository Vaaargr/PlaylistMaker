package com.example.playlistmaker.player.domain.api.interactors

interface PlayerInteractor {
    fun preparePlayer(url: String, onPreparedListener:() -> Unit, onCompletionListener:() -> Unit)
    fun getCurrentPosition(): Int
    fun release()
    fun start()
    fun pause()

    fun getDuration(): Int
}