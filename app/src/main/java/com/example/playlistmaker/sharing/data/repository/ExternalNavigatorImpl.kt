package com.example.playlistmaker.sharing.data.repository

import com.example.playlistmaker.sharing.data.clientsInterfaces.ExternalNavigatorClient
import com.example.playlistmaker.sharing.domain.api.ExternalNavigator
import com.example.playlistmaker.sharing.domain.model.EmailData

class ExternalNavigatorImpl(val client: ExternalNavigatorClient): ExternalNavigator {
    override fun shareLink(link: String) {
        client.shareLink(link)
    }

    override fun openLink(link: String) {
        client.openLink(link)
    }

    override fun openEmail(mail: EmailData) {
        client.openEmail(mail)
    }
}