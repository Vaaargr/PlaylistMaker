package com.example.playlistmaker.presentation.ui.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.TrackViewBinding
import com.example.playlistmaker.presentation.model.TrackForView
import com.example.playlistmaker.tools.Creator
import com.example.playlistmaker.tools.Formatter


class TrackViewHolder(private val item: View, private val roundingRadius: Float) : RecyclerView.ViewHolder(item) {
    private val binding = TrackViewBinding.bind(item)
    private val imageLoader = Creator.getImageLoaderClient()

    fun bind(track: TrackForView) {
        with(binding) {
            trackName.text = track.trackName
            artistName.text = track.artistName
            trackTime.text = Formatter.timeFormat(track.trackTimeMillis)

            imageLoader.loadImage(itemView.context, track.artworkUrl100, roundingRadius, albumImage)
        }
    }
}