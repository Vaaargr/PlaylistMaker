package com.example.playlistmaker.player.presentation.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.PlaylistPlayerViewBinding
import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView
import com.example.playlistmaker.player.presentation.adapter.PlaylistClickListener
import com.example.playlistmaker.tools.GlideClient

class PlaylistPlayerViewHolder (private val item: View): RecyclerView.ViewHolder(item) {
    private val binding = PlaylistPlayerViewBinding.bind(item)
    private val imageLoader = GlideClient()

    fun bind(playlist: PlaylistForView, plural: String, roundingRadius : Int, clickListener: PlaylistClickListener){
        item.setOnClickListener {
            clickListener.clickOnPlaylist(playlist)
        }

        with(binding){
            val tracksCountText = "${playlist.tracksCount} $plural"

            playlistPlayerName.text = playlist.name
            tracksPlayerCount.text = tracksCountText
            imageLoader.loadImage(itemView.context, playlist.imagePath, roundingRadius, playlistPlayerImage )
        }
    }
}