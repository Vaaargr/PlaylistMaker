package com.example.playlistmaker.player.domain.api.repositorys

interface PlayerRepository {
    fun preparePlayer(url: String, onPreparedListener:() -> Unit, onCompletionListener:() -> Unit)

    fun getCurrentPosition(): Int

    fun release()

    fun start()

    fun pause()

    fun getDuration(): Int
}