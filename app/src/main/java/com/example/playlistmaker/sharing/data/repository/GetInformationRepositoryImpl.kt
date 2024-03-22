package com.example.playlistmaker.sharing.data.repository

import com.example.playlistmaker.sharing.data.clientsInterfaces.GetInfoClient
import com.example.playlistmaker.sharing.domain.api.GetInformationRepository
import com.example.playlistmaker.sharing.domain.model.EmailData

class GetInformationRepositoryImpl(private val getter: GetInfoClient): GetInformationRepository {
    override fun getShareAppLink(): String {
        return getter.getShareAppLink()
    }

    override fun getSupportEmailData(): EmailData {
        return getter.getSupportEmailData()
    }

    override fun getTermsLink(): String {
        return getter.getTermsLink()
    }
}