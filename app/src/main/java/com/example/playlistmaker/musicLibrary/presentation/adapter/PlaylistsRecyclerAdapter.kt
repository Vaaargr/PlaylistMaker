package com.example.playlistmaker.musicLibrary.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.musicLibrary.presentation.adapter.viewHolder.PlaylistViewHolder
import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView
import java.util.ArrayList

class PlaylistsRecyclerAdapter(private val roundingRadius: Int, private val context: Context) :
    RecyclerView.Adapter<PlaylistViewHolder>() {
    private val playlists = ArrayList<PlaylistForView>()

    fun add(inputPlaylist: List<PlaylistForView>) {
        playlists.clear()
        playlists.addAll(inputPlaylist)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.playlist_view, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(
            playlists[position],
            definePlural(playlists[position].tracksCount),
            roundingRadius
        )
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    private fun definePlural(number: Int): String {
        return context.getString(
            when (number) {
                0 -> R.string.tracks_match_b
                1 -> R.string.track_match
                in 2..4 -> R.string.tracks_match_a
                in 5..20 -> R.string.tracks_match_b
                else -> {
                    val symbol = number % 10
                    when (symbol) {
                        1 -> R.string.tracks_unmatch
                        in 2..4 -> R.string.tracks_match_a
                        else -> R.string.tracks_match_a
                    }
                }
            }
        )
    }
}