package com.example.playlistmaker.musicLibrary.presentation.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistViewBinding
import com.example.playlistmaker.musicLibrary.presentation.adapter.clickListener.PlaylistClickListener
import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView
import com.example.playlistmaker.tools.Formatter
import com.example.playlistmaker.tools.GlideClient

class PlaylistViewHolder(private val item: View): RecyclerView.ViewHolder(item) {
    private val binding = PlaylistViewBinding.bind(item)
    private val imageLoader = GlideClient()

    fun bind(playlist: PlaylistForView, plural: String, roundingRadius : Int, listener: PlaylistClickListener){
        item.setOnClickListener {
            listener.clickOnPlaylist(playlist)
        }
        with(binding){
            val tracksCountText = "${playlist.tracksCount} $plural"

            playlistName.text = playlist.name
            tracksCount.text = tracksCountText
            imageLoader.loadImage(itemView.context, playlist.imagePath, roundingRadius, playlistImage )
        }
    }
}