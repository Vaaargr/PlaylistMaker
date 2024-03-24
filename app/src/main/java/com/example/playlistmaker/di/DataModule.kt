package com.example.playlistmaker.di

import android.content.Context
import android.media.MediaPlayer
import com.example.playlistmaker.R
import com.example.playlistmaker.player.data.clientIterfaces.PlayerClient
import com.example.playlistmaker.player.data.clientIterfaces.ReceiveTrackLocalClient
import com.example.playlistmaker.player.data.clients.ReceiveTrackShPrefsClient
import com.example.playlistmaker.player.data.mediaPlayer.MediaPlayerClient
import com.example.playlistmaker.search.data.clientInterfaces.NetworkClient
import com.example.playlistmaker.search.data.clientInterfaces.SearchHistoryClient
import com.example.playlistmaker.search.data.clientInterfaces.SendTrackLocalClient
import com.example.playlistmaker.search.data.clients.network.ITunesSearchApi
import com.example.playlistmaker.search.data.clients.network.RetrofitNetworkClient
import com.example.playlistmaker.search.data.clients.sharedPrefs.SearchHistoryShPrefsClient
import com.example.playlistmaker.search.data.clients.sharedPrefs.SendTrackShPrefsClient
import com.example.playlistmaker.settings.data.clientInterfaces.SettingsClient
import com.example.playlistmaker.settings.data.clients.SettingsShPrefClient
import com.example.playlistmaker.sharing.data.clients.ExternalNavigatorClientImpl
import com.example.playlistmaker.sharing.data.clients.GetInfoFromContextClient
import com.example.playlistmaker.sharing.data.clientsInterfaces.ExternalNavigatorClient
import com.example.playlistmaker.sharing.data.clientsInterfaces.GetInfoClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(androidContext().resources.getString(R.string.i_tunes_base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesSearchApi::class.java)
    }

    single {
        androidContext().getSharedPreferences(
            androidContext().resources.getString(R.string.sh_prefs_name),
            Context.MODE_PRIVATE
        )
    }

    single {
        MediaPlayer()
    }

    single<ExternalNavigatorClient> {
        ExternalNavigatorClientImpl(androidContext())
    }

    single<GetInfoClient> {
        GetInfoFromContextClient(androidContext())
    }

    single<SettingsClient> {
        SettingsShPrefClient(get(), androidContext().resources.getString(R.string.theme_key))
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }

    single<SendTrackLocalClient> {
        SendTrackShPrefsClient(get(), androidContext().resources.getString(R.string.track_key))
    }

    single<SearchHistoryClient> {
        SearchHistoryShPrefsClient(
            get(),
            androidContext().resources.getString(R.string.search_history)
        )
    }

    single<ReceiveTrackLocalClient> {
        ReceiveTrackShPrefsClient(get(), androidContext().resources.getString(R.string.track_key))
    }

    single<PlayerClient> {
        MediaPlayerClient(get())
    }
}