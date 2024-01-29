package com.example.playlistmaker.data.clientIterfaces

import com.example.playlistmaker.data.dto.Response

interface NetworkClient {
fun makeRequest(request: Any): Response
}