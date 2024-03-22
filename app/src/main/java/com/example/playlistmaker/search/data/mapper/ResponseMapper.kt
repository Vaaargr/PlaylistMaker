package com.example.playlistmaker.search.data.mapper

import com.example.playlistmaker.search.data.dto.ITunesResponse
import com.example.playlistmaker.search.domain.entity.SearchResponse

object ResponseMapper {
    fun map(iTunesResponse: ITunesResponse): SearchResponse {
        return SearchResponse(iTunesResponse.resultCode,
            iTunesResponse.results.map { TrackDtoMapper.trackDtoToTrackMap(it) })
    }
}