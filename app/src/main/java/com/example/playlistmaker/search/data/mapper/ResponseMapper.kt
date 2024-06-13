package com.example.playlistmaker.search.data.mapper

import com.example.playlistmaker.search.data.dto.ITunesResponse
import com.example.playlistmaker.search.domain.entity.SearchResponse

class ResponseMapper(private val trackMapper: TrackDtoMapper) {
    fun map(iTunesResponse: ITunesResponse): SearchResponse {
        return SearchResponse(iTunesResponse.resultCode,
            iTunesResponse.results.map { trackMapper.trackDtoToTrackMap(it) })
    }
}