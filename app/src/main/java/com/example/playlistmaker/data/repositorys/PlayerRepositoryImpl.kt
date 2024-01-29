package com.example.playlistmaker.data.repositorys

import com.example.playlistmaker.data.clientIterfaces.PlayerClient
import com.example.playlistmaker.domain.api.repositorys.PlayerRepository

class PlayerRepositoryImpl(private val playerClient: PlayerClient): PlayerRepository {
    override fun preparePlayer(url: String, onPreparedListener:() -> Unit, onCompletionListener:() -> Unit){
        playerClient.preparePlayer(url,onPreparedListener,onCompletionListener)
    }
    override fun getCurrentPosition(): Int{
        return playerClient.getCurrentPosition()
    }
    override fun release(){
        playerClient.release()
    }
    override fun start(){
        playerClient.start()
    }
    override fun pause(){
        playerClient.pause()
    }

    override fun getDuration(): Int{
        return playerClient.getDuration()
    }
}