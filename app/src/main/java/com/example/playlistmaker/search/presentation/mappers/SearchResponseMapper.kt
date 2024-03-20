package com.example.playlistmaker.search.presentation.mappers

import com.example.playlistmaker.search.domain.entity.SearchResponse
import com.example.playlistmaker.search.presentation.model.ResponseResult
import com.example.playlistmaker.search.presentation.state.SearchActivityState

object SearchResponseMapper {
    fun map(searchResponse: SearchResponse): SearchActivityState.Response {
        return if (searchResponse.resultCode == 200) {
            if (searchResponse.trackList.isNotEmpty()) {
                SearchActivityState.Response(
                    ResponseResult.GOOD,
                    searchResponse.trackList.map { TrackViewMapper.trackToTrackForViewMap(it) }
                )
            } else {
                SearchActivityState.Response(ResponseResult.EMPTY, emptyList())
            }
        } else {
            SearchActivityState.Response(ResponseResult.ERROR, emptyList())
        }
    }
}