package com.example.playlistmaker.tools

import java.text.SimpleDateFormat
import java.util.Locale

object Formatter {

    fun timeFormat(timeInt: Int): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(timeInt)
    }
}