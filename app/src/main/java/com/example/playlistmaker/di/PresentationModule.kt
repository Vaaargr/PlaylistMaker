package com.example.playlistmaker.di

import com.example.playlistmaker.search.presentation.mappers.SearchResponseMapper
import com.example.playlistmaker.search.presentation.mappers.TrackViewMapper
import org.koin.dsl.module

val presentationModule = module {
    factory { TrackViewMapper() }

    factory { SearchResponseMapper(get()) }
}