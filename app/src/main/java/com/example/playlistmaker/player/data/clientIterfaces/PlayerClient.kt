package com.example.playlistmaker.player.data.clientIterfaces

interface PlayerClient {
    fun preparePlayer(url: String, onPreparedListener:() -> Unit, onCompletionListener:() -> Unit)

    fun getCurrentPosition(): Int

    fun release()

    fun start()

    fun pause()

    fun getDuration(): Int
}