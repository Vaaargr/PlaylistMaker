package com.example.playlistmaker.presentation.mappers

import com.example.playlistmaker.domain.entity.SearchResponse
import com.example.playlistmaker.presentation.model.ResponseForView

object SearchResponseMapper {
    fun map(searchResponse: SearchResponse): ResponseForView{
        return ResponseForView(
            resultCode = searchResponse.resultCode,
            results = searchResponse.trackList.map {
                TrackViewMapper.trackToTrackForViewMap(it)
            }
        )
    }
}