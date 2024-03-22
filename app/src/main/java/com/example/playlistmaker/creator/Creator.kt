package com.example.playlistmaker.creator

import android.app.Application
import android.content.SharedPreferences
import com.example.playlistmaker.search.data.clientInterfaces.SendTrackLocalClient
import com.example.playlistmaker.search.data.clientInterfaces.NetworkClient
import com.example.playlistmaker.player.data.clientIterfaces.PlayerClient
import com.example.playlistmaker.player.data.clientIterfaces.ReceiveTrackLocalClient
import com.example.playlistmaker.player.data.clients.ReceiveTrackShPrefsClient
import com.example.playlistmaker.search.data.clientInterfaces.SearchHistoryClient
import com.example.playlistmaker.player.data.repositorys.PlayerRepositoryImpl
import com.example.playlistmaker.player.data.repositorys.ReceiveTrackUseCaseRepositoryImpl
import com.example.playlistmaker.player.data.mediaPlayer.MediaPlayerClient
import com.example.playlistmaker.search.data.clients.network.RetrofitNetworkClient
import com.example.playlistmaker.search.data.clients.sharedPrefs.SharedPrefsSearchHistoryClient
import com.example.playlistmaker.search.data.repositorys.SearchHistoryRepositoryImpl
import com.example.playlistmaker.search.data.repositorys.SearchTrackRepositoryImpl
import com.example.playlistmaker.player.domain.api.interactors.PlayerInteractor
import com.example.playlistmaker.search.domain.api.interactors.SearchHistoryInteractor
import com.example.playlistmaker.search.domain.api.interactors.SearchTrackUseCase
import com.example.playlistmaker.player.domain.api.repositorys.PlayerRepository
import com.example.playlistmaker.player.domain.api.interactors.ReceiveTrackUseCase
import com.example.playlistmaker.search.domain.api.repositorys.SearchHistoryRepository
import com.example.playlistmaker.search.domain.api.repositorys.SearchTrackRepository
import com.example.playlistmaker.player.domain.api.repositorys.ReceiveTrackUseCaseRepository
import com.example.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.example.playlistmaker.search.domain.impl.SearchHistoryInteractorImpl
import com.example.playlistmaker.search.domain.impl.SearchTrackUseCaseImpl
import com.example.playlistmaker.player.domain.impl.ReceiveTrackUseCaseImpl
import com.example.playlistmaker.search.data.clients.sharedPrefs.SendTrackShPrefsClient
import com.example.playlistmaker.search.data.repositorys.SendTrackRepositoryImpl
import com.example.playlistmaker.search.domain.api.interactors.SendTrackUseCase
import com.example.playlistmaker.search.domain.api.repositorys.SendTrackRepository
import com.example.playlistmaker.search.domain.impl.SendTrackUseCaseImpl
import com.example.playlistmaker.settings.data.clientInterfaces.SettingsClient
import com.example.playlistmaker.settings.data.clients.SettingsShPrefClient
import com.example.playlistmaker.settings.data.repository.SettingsRepositoryImpl
import com.example.playlistmaker.settings.domain.api.SettingsInteractor
import com.example.playlistmaker.settings.domain.api.SettingsRepository
import com.example.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.example.playlistmaker.sharing.data.clients.ExternalNavigatorClientImpl
import com.example.playlistmaker.sharing.data.clients.GetInfoFromContextClient
import com.example.playlistmaker.sharing.data.clientsInterfaces.ExternalNavigatorClient
import com.example.playlistmaker.sharing.data.clientsInterfaces.GetInfoClient
import com.example.playlistmaker.sharing.data.repository.ExternalNavigatorImpl
import com.example.playlistmaker.sharing.data.repository.GetInformationRepositoryImpl
import com.example.playlistmaker.sharing.domain.api.ExternalNavigator
import com.example.playlistmaker.sharing.domain.api.GetInformationRepository
import com.example.playlistmaker.sharing.domain.api.SharingInteractor
import com.example.playlistmaker.sharing.domain.impl.SharingInteractorImpl

object Creator {
    private lateinit var prefs: SharedPreferences
    private lateinit var themeKey: String
    private lateinit var trackKey: String
    private lateinit var app: Application


    fun init(prefs: SharedPreferences, themeKey: String, trackKey: String, app: Application,) {
        Creator.prefs = prefs
        Creator.themeKey = themeKey
        Creator.trackKey = trackKey
        Creator.app = app
    }

    private fun getReceiveTrackLocalClient(): ReceiveTrackLocalClient {
        return ReceiveTrackShPrefsClient(sharedPreferences = prefs, trackKey =  trackKey)
    }

    private fun getReceiveTrackUseCaseRepository(): ReceiveTrackUseCaseRepository {
        return ReceiveTrackUseCaseRepositoryImpl(getReceiveTrackLocalClient())
    }

    fun getReceiveTrackUseCase(): ReceiveTrackUseCase {
        return ReceiveTrackUseCaseImpl(getReceiveTrackUseCaseRepository())
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

    private fun getNetworkClient(): NetworkClient {
        return RetrofitNetworkClient()
    }

    private fun getSearchTrackRepository(): SearchTrackRepository {
        return SearchTrackRepositoryImpl(getNetworkClient())
    }

    fun getSearchTrackUseCase(): SearchTrackUseCase {
        return SearchTrackUseCaseImpl(getSearchTrackRepository())
    }

    private fun getSearchHistoryClient(): SearchHistoryClient {
        return SharedPrefsSearchHistoryClient(sharedPreferences = prefs)
    }

    private fun getSearchHistoryRepository(): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(getSearchHistoryClient())
    }

    fun getSearchHistoryInteractor(): SearchHistoryInteractor {
        return SearchHistoryInteractorImpl(getSearchHistoryRepository())
    }

    private fun getSettingsClient(): SettingsClient {
        return SettingsShPrefClient(prefs = prefs, key = themeKey)
    }

    private fun getSettingsRepository(): SettingsRepository {
        return SettingsRepositoryImpl(getSettingsClient())
    }

    fun getSettingsInteractor(): SettingsInteractor {
        return SettingsInteractorImpl(getSettingsRepository())
    }

    private fun getGetInfoClient(): GetInfoClient {
        return GetInfoFromContextClient(app.applicationContext)
    }

    private fun getGetInformationRepository(): GetInformationRepository {
        return GetInformationRepositoryImpl(getGetInfoClient())
    }

    private fun getExternalNavigationClient(): ExternalNavigatorClient{
        return ExternalNavigatorClientImpl(app.applicationContext)
    }

    private fun getExternalNavigator(): ExternalNavigator{
        return ExternalNavigatorImpl(getExternalNavigationClient())
    }

    fun getSharingInteractor(): SharingInteractor{
        return SharingInteractorImpl(getExternalNavigator(), getGetInformationRepository())
    }

    private fun getSendTrackLocalClient(): SendTrackLocalClient{
        return SendTrackShPrefsClient(sharedPreferences = prefs, trackKey = trackKey)
    }

    private fun getSendTrackRepository(): SendTrackRepository{
        return SendTrackRepositoryImpl(getSendTrackLocalClient())
    }

    fun getSendTrackUseCase(): SendTrackUseCase{
        return SendTrackUseCaseImpl(getSendTrackRepository())
    }
}