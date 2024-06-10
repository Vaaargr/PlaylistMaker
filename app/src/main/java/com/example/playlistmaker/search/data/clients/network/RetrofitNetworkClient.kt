package com.example.playlistmaker.search.data.clients.network

import com.example.playlistmaker.search.data.clientInterfaces.NetworkClient
import com.example.playlistmaker.search.data.dto.ITunesRequest
import com.example.playlistmaker.search.data.dto.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitNetworkClient(private val iTunesService: ITunesSearchApi) : NetworkClient {

    override suspend fun makeRequest(request: Any): Response {
        if (request !is ITunesRequest) {
            return Response().apply { resultCode = 400 }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = iTunesService.search(request.request)
                response.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }
}