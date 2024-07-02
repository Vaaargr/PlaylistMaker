package com.example.playlistmaker.musicLibrary.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.musicLibrary.presentation.adapter.clickListener.SavedTrackClickListener
import com.example.playlistmaker.musicLibrary.presentation.adapter.viewHolder.SavedTracksViewHolder
import com.example.playlistmaker.search.presentation.model.TrackForView

class SavedTracksRecyclerAdapter(
    private val listener: SavedTrackClickListener,
    private val roundingRadius: Int
) : RecyclerView.Adapter<SavedTracksViewHolder>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedTracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return SavedTracksViewHolder(view, roundingRadius)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: SavedTracksViewHolder, position: Int) {
        holder.bind(tracks[position], listener)
    }
}