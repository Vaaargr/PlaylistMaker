package com.example.playlistmaker.search.data.clientInterfaces

import com.example.playlistmaker.search.data.dto.Response

interface NetworkClient {
fun makeRequest(request: Any): Response
}