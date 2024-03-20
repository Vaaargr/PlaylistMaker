package com.example.playlistmaker.search.presentation.state

import com.example.playlistmaker.search.presentation.model.ResponseResult
import com.example.playlistmaker.search.presentation.model.TrackForView

sealed class SearchActivityState {
    data class History(val trackList: List<TrackForView>): SearchActivityState()
    data class Response(
        val responseResult: ResponseResult,
        val trackList: List<TrackForView>
    ): SearchActivityState()
}