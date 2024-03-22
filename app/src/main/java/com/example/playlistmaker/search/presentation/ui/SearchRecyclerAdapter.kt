package com.example.playlistmaker.search.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.search.presentation.model.TrackForView

class SearchRecyclerAdapter(private val listener: TrackClickListener, private val roundingRadius: Int) :
    RecyclerView.Adapter<TrackViewHolder>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view, roundingRadius)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position], listener)
    }
}