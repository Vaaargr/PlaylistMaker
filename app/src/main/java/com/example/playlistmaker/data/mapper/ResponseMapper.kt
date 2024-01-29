package com.example.playlistmaker.data.mapper

import com.example.playlistmaker.data.dto.ITunesResponse
import com.example.playlistmaker.domain.entity.SearchResponse

object ResponseMapper {
    fun map(iTunesResponse: ITunesResponse): SearchResponse {
        return SearchResponse(iTunesResponse.resultCode,
            iTunesResponse.results.map { TrackDtoMapper.trackDtoToTrackMap(it) })
    }
}