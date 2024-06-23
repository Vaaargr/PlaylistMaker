package com.example.playlistmaker.musicLibrary.presentation.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.TrackViewBinding
import com.example.playlistmaker.musicLibrary.presentation.adapter.SavedTrackClickListener
import com.example.playlistmaker.search.presentation.model.TrackForView
import com.example.playlistmaker.tools.Formatter
import com.example.playlistmaker.tools.GlideClient

class SavedTracksViewHolder (private val item: View, private val roundingRadius: Int) : RecyclerView.ViewHolder(item) {
    private val binding = TrackViewBinding.bind(item)
    private val imageLoader = GlideClient()

    fun bind(track: TrackForView, onClickListener: SavedTrackClickListener) {
        item.setOnClickListener {
            onClickListener.clickOnTrack(track)
        }
        with(binding) {
            trackViewTrackName.text = track.trackName
            trackArtistName.text = track.artistName
            trackViewTrackTime.text = Formatter.timeFormat(track.trackTimeMillis)

            imageLoader.loadImage(itemView.context, track.artworkUrl100, roundingRadius, trackAlbumImage)
        }
    }
}