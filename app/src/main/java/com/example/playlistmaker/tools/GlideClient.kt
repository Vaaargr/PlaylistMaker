package com.example.playlistmaker.tools

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R

class GlideClient {
    fun loadImage(context: Context, url: String?, roundingRadius: Int, view: ImageView) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(roundingRadius))
            .into(view)
    }
}