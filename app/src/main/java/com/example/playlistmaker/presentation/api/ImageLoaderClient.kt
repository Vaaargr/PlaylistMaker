package com.example.playlistmaker.presentation.api

import android.content.Context
import android.widget.ImageView

interface ImageLoaderClient {
    fun loadImage(context: Context, url: String?, roundingRadius: Float, view: ImageView)
}