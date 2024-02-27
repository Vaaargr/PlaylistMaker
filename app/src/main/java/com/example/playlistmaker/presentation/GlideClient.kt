package com.example.playlistmaker.presentation

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R

class GlideClient {
    fun loadImage(context: Context, url: String?, roundingRadius: Float, view: ImageView) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(dpToPx(roundingRadius, context)))
            .into(view)
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}