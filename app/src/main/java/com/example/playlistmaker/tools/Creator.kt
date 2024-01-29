package com.example.playlistmaker.tools

import android.content.SharedPreferences
import com.example.playlistmaker.data.clientIterfaces.LocalClient
import com.example.playlistmaker.data.clientIterfaces.NetworkClient
import com.example.playlistmaker.data.clientIterfaces.PlayerClient
import com.example.playlistmaker.data.clientIterfaces.SearchHistoryClient
import com.example.playlistmaker.data.repositorys.PlayerRepositoryImpl
import com.example.playlistmaker.data.repositorys.TrackExchangeRepositoryImpl
import com.example.playlistmaker.data.clients.mediaPlayer.MediaPlayerClient
import com.example.playlistmaker.data.clients.network.RetrofitNetworkClient
import com.example.playlistmaker.data.clients.sharedPrefs.SharedPrefsClient
import com.example.playlistmaker.data.clients.sharedPrefs.SharedPrefsSearchHistoryClient
import com.example.playlistmaker.data.repositorys.SearchHistoryRepositoryImpl
import com.example.playlistmaker.data.repositorys.SearchTrackRepositoryImpl
import com.example.playlistmaker.domain.api.interactors.PlayerInteractor
import com.example.playlistmaker.domain.api.interactors.SearchHistoryInteractor
import com.example.playlistmaker.domain.api.interactors.SearchTrackUseCase
import com.example.playlistmaker.domain.api.repositorys.PlayerRepository
import com.example.playlistmaker.domain.api.interactors.TrackExchangeInteract
import com.example.playlistmaker.domain.api.repositorys.SearchHistoryRepository
import com.example.playlistmaker.domain.api.repositorys.SearchTrackRepository
import com.example.playlistmaker.domain.api.repositorys.TrackExchangeRepository
import com.example.playlistmaker.domain.impl.PlayerInteractorImpl
import com.example.playlistmaker.domain.impl.SearchHistoryInteractorImpl
import com.example.playlistmaker.domain.impl.SearchTrackUseCaseImpl
import com.example.playlistmaker.domain.impl.TrackExchangeInteractImpl
import com.example.playlistmaker.presentation.api.ImageLoaderClient
import com.example.playlistmaker.presentation.impl.GlideClient

object Creator {
    private lateinit var prefs: SharedPreferences

    fun init(prefs: SharedPreferences){
        this.prefs = prefs
    }

    fun getPrefs(): SharedPreferences{
        return prefs
    }

    private fun getSharedPrefsClient(): LocalClient {
        return SharedPrefsClient(getPrefs())
    }

    private fun getTrackExchangeRepositoryImpl(): TrackExchangeRepository {
        return TrackExchangeRepositoryImpl(getSharedPrefsClient())
    }

    fun getTrackExchangeInteractImpl(): TrackExchangeInteract {
        return TrackExchangeInteractImpl(getTrackExchangeRepositoryImpl())
    }

    fun getImageLoaderClient(): ImageLoaderClient{
        return GlideClient()
    }

    private fun getPlayerClient(): PlayerClient {
        return MediaPlayerClient()
    }

    private fun getPlayerRepository(): PlayerRepository {
        return PlayerRepositoryImpl(getPlayerClient())
    }

    fun getPlayerInteractor(): PlayerInteractor {
        return PlayerInteractorImpl(getPlayerRepository())
    }

    private fun getNetworkClient(): NetworkClient{
        return RetrofitNetworkClient()
    }

    private fun getSearchTrackRepository(): SearchTrackRepository{
        return SearchTrackRepositoryImpl(getNetworkClient())
    }

    fun getSearchTrackUseCase(): SearchTrackUseCase{
        return SearchTrackUseCaseImpl(getSearchTrackRepository())
    }

    private fun getSearchHistoryClient(): SearchHistoryClient{
        return SharedPrefsSearchHistoryClient(getPrefs())
    }

    private fun getSearchHistoryRepository(): SearchHistoryRepository{
        return SearchHistoryRepositoryImpl(getSearchHistoryClient())
    }

    fun getSearchHistoryInteractor(): SearchHistoryInteractor{
        return SearchHistoryInteractorImpl(getSearchHistoryRepository())
    }
}