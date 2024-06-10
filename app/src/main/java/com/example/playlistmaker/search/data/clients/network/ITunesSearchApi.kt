package com.example.playlistmaker.search.data.clients.network

import com.example.playlistmaker.search.data.dto.ITunesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesSearchApi {
    @GET("/search?entity=song ")
    suspend fun search(@Query("term") text: String): ITunesResponse
}