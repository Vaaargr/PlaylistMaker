package com.example.playlistmaker.sharing.domain.api

import com.example.playlistmaker.sharing.domain.model.EmailData

interface GetInformationRepository {

    fun getShareAppLink(): String

    fun getSupportEmailData(): EmailData

    fun getTermsLink(): String
}