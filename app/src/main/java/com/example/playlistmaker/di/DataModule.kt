package com.example.playlistmaker.di

import android.content.Context
import android.media.MediaPlayer
import androidx.room.Room
import com.example.playlistmaker.R
import com.example.playlistmaker.musicLibrary.data.database.TrackDatabase
import com.example.playlistmaker.musicLibrary.data.mapper.PlaylistsEntityMapper
import com.example.playlistmaker.musicLibrary.data.mapper.TrackEntityMapper
import com.example.playlistmaker.player.data.clientIterfaces.PlayerClient
import com.example.playlistmaker.player.data.mediaPlayer.MediaPlayerClient
import com.example.playlistmaker.search.data.clientInterfaces.NetworkClient
import com.example.playlistmaker.search.data.clientInterfaces.SearchHistoryClient
import com.example.playlistmaker.search.data.clientInterfaces.SendTrackLocalClient
import com.example.playlistmaker.search.data.clients.network.ITunesSearchApi
import com.example.playlistmaker.search.data.clients.network.RetrofitNetworkClient
import com.example.playlistmaker.search.data.clients.sharedPrefs.SearchHistoryShPrefsClient
import com.example.playlistmaker.search.data.clients.sharedPrefs.SendTrackShPrefsClient
import com.example.playlistmaker.search.data.mapper.ResponseMapper
import com.example.playlistmaker.search.data.mapper.TrackDtoMapper
import com.example.playlistmaker.settings.data.clientInterfaces.SettingsClient
import com.example.playlistmaker.settings.data.clients.SettingsShPrefClient
import com.example.playlistmaker.sharing.data.clients.ExternalNavigatorClientImpl
import com.example.playlistmaker.sharing.data.clients.GetInfoFromContextClient
import com.example.playlistmaker.sharing.data.clientsInterfaces.ExternalNavigatorClient
import com.example.playlistmaker.sharing.data.clientsInterfaces.GetInfoClient
import com.google.gson.Gson
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

    factory {
        MediaPlayer()
    }

    factory {
        Gson()
    }

    single<ExternalNavigatorClient> {
        ExternalNavigatorClientImpl(androidContext())
    }

    single<GetInfoClient> {
        GetInfoFromContextClient(androidContext())
    }

    single<SettingsClient> {
        SettingsShPrefClient(get(), androidContext().resources.getString(R.string.theme_key), get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }

    single<SendTrackLocalClient> {
        SendTrackShPrefsClient(
            get(),
            androidContext().resources.getString(R.string.track_key),
            get()
        )
    }

    single<SearchHistoryClient> {
        SearchHistoryShPrefsClient(
            get(),
            androidContext().resources.getString(R.string.search_history),
            get()
        )
    }

    factory<PlayerClient> {
        MediaPlayerClient(get())
    }

    single {
        Room.databaseBuilder(androidContext(), TrackDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration().build()
    }

    factory { TrackEntityMapper() }

    factory { TrackDtoMapper() }

    factory { ResponseMapper(get()) }

    factory { PlaylistsEntityMapper(get()) }
}