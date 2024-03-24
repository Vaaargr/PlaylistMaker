package com.example.playlistmaker.search.data.clients.network

import com.example.playlistmaker.search.data.clientInterfaces.NetworkClient
import com.example.playlistmaker.search.data.dto.ITunesRequest
import com.example.playlistmaker.search.data.dto.Response

class RetrofitNetworkClient(private val iTunesService: ITunesSearchApi) : NetworkClient {

    override fun makeRequest(request: Any): Response {
        return if (request is ITunesRequest) {
            val response = iTunesService.search(request.request).execute()
            val body = response.body() ?: Response()
            body.apply { resultCode = response.code() }
        } else {
            Response().apply { resultCode = 400 }
        }
    }
}