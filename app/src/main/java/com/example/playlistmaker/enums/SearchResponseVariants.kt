package com.example.playlistmaker.enums

enum class SearchResponseVariants(val code: Int) {
    RESPONSE_ERROR(0),
    GOOD_RESPONSE(1),
    EMPTY_RESPONSE(2)
}