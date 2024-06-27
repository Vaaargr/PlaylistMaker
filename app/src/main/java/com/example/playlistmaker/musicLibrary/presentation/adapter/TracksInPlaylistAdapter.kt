package com.example.playlistmaker.musicLibrary.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.musicLibrary.presentation.adapter.clickListener.OnTrackInPlaylistClickListener
import com.example.playlistmaker.musicLibrary.presentation.adapter.viewHolder.TracksInPlaylistViewHolder
import com.example.playlistmaker.search.presentation.model.TrackForView

class TracksInPlaylistAdapter(
    private val listener: OnTrackInPlaylistClickListener,
    private val roundingRadius: Int
) :
    RecyclerView.Adapter<TracksInPlaylistViewHolder>() {
    private val tracks = ArrayList<TrackForView>()

    fun add(inputTracks: List<TrackForView>) {
        tracks.clear()
        tracks.addAll(inputTracks)
        notifyDataSetChanged()
    }

    fun clearList() {
        tracks.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksInPlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TracksInPlaylistViewHolder(view, roundingRadius)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: TracksInPlaylistViewHolder, position: Int) {
        holder.bind(tracks[position], listener)
    }
}