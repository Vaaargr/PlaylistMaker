package com.example.playlistmaker.sharing.domain.model

data class EmailData(
    val extraSubject: String,
    val extraText: String,
    val data: String,
)