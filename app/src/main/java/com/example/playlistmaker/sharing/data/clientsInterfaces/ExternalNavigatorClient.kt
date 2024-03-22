package com.example.playlistmaker.sharing.data.clientsInterfaces

import com.example.playlistmaker.sharing.domain.model.EmailData

interface ExternalNavigatorClient {

    fun shareLink(link: String)

    fun openLink(link: String)

    fun openEmail(mail: EmailData)
}