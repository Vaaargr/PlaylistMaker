package com.example.playlistmaker.sharing.data.clients

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.sharing.data.clientsInterfaces.GetInfoClient
import com.example.playlistmaker.sharing.domain.model.EmailData

class GetInfoFromContextClient(val context: Context) : GetInfoClient {
    override fun getShareAppLink(): String {
        return context.getString(R.string.share_address)
    }

    override fun getSupportEmailData(): EmailData {
        return EmailData(
            context.getString(R.string.message_subject),
            context.getString(R.string.message_body),
            context.getString(R.string.user_mail)
        )
    }

    override fun getTermsLink(): String {
        return context.getString(R.string.term_address)
    }
}