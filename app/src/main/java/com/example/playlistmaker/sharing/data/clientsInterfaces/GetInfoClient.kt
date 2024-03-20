package com.example.playlistmaker.sharing.data.clientsInterfaces

import com.example.playlistmaker.sharing.domain.model.EmailData

interface GetInfoClient {

    fun getShareAppLink(): String

    fun getSupportEmailData(): EmailData

    fun getTermsLink(): String
}