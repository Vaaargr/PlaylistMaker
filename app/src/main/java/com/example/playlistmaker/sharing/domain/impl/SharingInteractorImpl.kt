package com.example.playlistmaker.sharing.domain.impl

import com.example.playlistmaker.sharing.domain.api.ExternalNavigator
import com.example.playlistmaker.sharing.domain.api.GetInformationRepository
import com.example.playlistmaker.sharing.domain.api.SharingInteractor
import com.example.playlistmaker.sharing.domain.model.EmailData

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
    private val getter: GetInformationRepository
) : SharingInteractor {
    override fun shareApp() {
        externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms() {
        externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport() {
        externalNavigator.openEmail(getSupportEmailData())
    }

    private fun getShareAppLink(): String{
        return getter.getShareAppLink()
    }

    private fun getSupportEmailData(): EmailData{
        return getter.getSupportEmailData()
    }

    private fun getTermsLink(): String{
        return getter.getTermsLink()
    }
}