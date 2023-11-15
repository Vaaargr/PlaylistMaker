package com.example.playlistmaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class SearchRecyclerAdapter :
    RecyclerView.Adapter<SearchRecyclerAdapter.TrackViewHolder>() {
    private val tracks = ArrayList<Track>()

    fun add(inputTracks: List<Track>) {
        tracks.addAll(inputTracks)
        notifyDataSetChanged()
    }

    fun clearList() {
        tracks.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    class TrackViewHolder(private val itemView: View) : ViewHolder(itemView) {
        private val trackNameView = itemView.findViewById<TextView>(R.id.trackName)
        private val artistNameView = itemView.findViewById<TextView>(R.id.artistName)
        private val trackTimeView = itemView.findViewById<TextView>(R.id.trackTime)
        private val albumImageView = itemView.findViewById<ImageView>(R.id.albumImage)

        fun bind(track: Track) {
            trackNameView.text = track.trackName
            artistNameView.text = track.artistName
            trackTimeView.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
            Glide.with(itemView.context)
                .load(track.artworkUrl100)
                .placeholder(R.drawable.placeholder)
                .transform(RoundedCorners(2))
                .into(albumImageView)
        }
    }
}