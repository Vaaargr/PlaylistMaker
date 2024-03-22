package com.example.playlistmaker.sharing.data.clients

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.sharing.data.clientsInterfaces.ExternalNavigatorClient
import com.example.playlistmaker.sharing.domain.model.EmailData

class ExternalNavigatorClientImpl(val context: Context) : ExternalNavigatorClient {
    override fun shareLink(link: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, link)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    override fun openLink(link: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(link)
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    override fun openEmail(mail: EmailData) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            mail.extraSubject
        )
        intent.putExtra(
            Intent.EXTRA_TEXT,
            mail.extraText
        )
        intent.data = Uri.parse(mail.data)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
}