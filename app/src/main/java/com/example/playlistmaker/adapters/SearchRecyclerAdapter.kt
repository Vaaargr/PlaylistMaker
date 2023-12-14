package com.example.playlistmaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.TrackViewBinding
import com.example.playlistmaker.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class SearchRecyclerAdapter(private val listener: TrackClickListener, private val roundingRadius: Int) :
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
        return TrackViewHolder(view, roundingRadius)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            listener.onClick(tracks[position])
        }
    }

    class TrackViewHolder(private val item: View, private val roundingRadius: Int) : ViewHolder(item) {
        private val binding = TrackViewBinding.bind(item)

        fun bind(track: Track) {
            with(binding) {
                trackName.text = track.trackName
                artistName.text = track.artistName
                trackTime.text =
                    SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
                Glide.with(itemView.context)
                    .load(track.artworkUrl100)
                    .placeholder(R.drawable.placeholder)
                    .transform(RoundedCorners(roundingRadius))
                    .into(binding.albumImage)
            }
        }
    }

    interface TrackClickListener {
        fun onClick(track: Track)
    }
}