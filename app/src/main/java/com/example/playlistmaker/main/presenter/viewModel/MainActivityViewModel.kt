package com.example.playlistmaker.main.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.main.presenter.state.NavigationState
import com.example.playlistmaker.tools.SingleEventLiveData

class MainActivityViewModel : ViewModel() {
    private val navigationLiveData = SingleEventLiveData<NavigationState>()

    fun getNavigation(): LiveData<NavigationState> = navigationLiveData

    fun goTo(navigationState: NavigationState) {
        navigationLiveData.setValue(navigationState)
    }
}