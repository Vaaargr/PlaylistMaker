package com.example.playlistmaker.player.data.mediaPlayer

import android.media.MediaPlayer
import com.example.playlistmaker.player.data.clientIterfaces.PlayerClient

class MediaPlayerClient(private val mediaPlayer: MediaPlayer) : PlayerClient {

    override fun preparePlayer(
        url: String,
        onPreparedListener: () -> Unit,
        onCompletionListener: () -> Unit
    ) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            onPreparedListener.invoke()
        }
        mediaPlayer.setOnCompletionListener {
            onCompletionListener.invoke()
        }
    }

    override fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun start() {
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun getDuration(): Int {
        return mediaPlayer.duration
    }
}