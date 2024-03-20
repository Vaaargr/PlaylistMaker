package com.example.playlistmaker.search.data.clients.network

import com.example.playlistmaker.search.data.clientInterfaces.NetworkClient
import com.example.playlistmaker.search.data.dto.ITunesRequest
import com.example.playlistmaker.search.data.dto.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient: NetworkClient {
    private val baseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val iTunesService = retrofit.create(ITunesSearchApi::class.java)
    override fun makeRequest(request: Any): Response {
        return if (request is ITunesRequest){
            val response = iTunesService.search(request.request).execute()
            val body = response.body() ?: Response()
            body.apply { resultCode = response.code() }
        } else{
            Response().apply { resultCode = 400 }
        }
    }
}