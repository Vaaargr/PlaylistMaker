package com.example.playlistmaker.musicLibrary.presentation.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.musicLibrary.domain.api.interactors.SavePlaylistUseCase
import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView
import com.example.playlistmaker.musicLibrary.presentation.mapper.PlaylistForViewMapper
import com.example.playlistmaker.musicLibrary.presentation.states.NewPlaylistFragmentState
import kotlinx.coroutines.launch

class NewPlaylistFragmentViewModel(
    private val savePlaylistUseCase: SavePlaylistUseCase,
    private val mapper: PlaylistForViewMapper
) : ViewModel() {

    private val buttonStateLiveData =
        MutableLiveData<NewPlaylistFragmentState>(NewPlaylistFragmentState.disable)
    private val nameLiveData = MutableLiveData<String>("")
    private val descriptionLiveData = MutableLiveData<String?>()
    private val imageLiveData = MutableLiveData<Uri?>()

    fun observeButtonState(): LiveData<NewPlaylistFragmentState> = buttonStateLiveData

    fun observeImageLD(): LiveData<Uri?> = imageLiveData

    fun getUri(): Uri?{
        return imageLiveData.value
    }

    fun getName(): String?{
        return nameLiveData.value
    }

    private fun setState(state: NewPlaylistFragmentState) {
        buttonStateLiveData.postValue(state)
    }

    private fun setName(name: String) {
        nameLiveData.postValue(name)
    }

    fun setDescription(description: String?) {
        descriptionLiveData.postValue(description)
    }

    private fun setImage(uri: Uri?) {
        imageLiveData.postValue(uri)
    }

    fun changeImage(uri: Uri?) {
        if (uri != null) {
            setImage(uri)
        }
    }

    fun nameChanged(name: String?) {
        if (name.isNullOrEmpty()) {
            setState(NewPlaylistFragmentState.disable)
        } else {
            setState(NewPlaylistFragmentState.enable)
            setName(name)
        }
    }

    fun checkData(): Boolean {
        return (nameLiveData.value.isNullOrEmpty() && descriptionLiveData.value.isNullOrEmpty() && imageLiveData.value == null)
    }

    fun clearDate() {
        setState(NewPlaylistFragmentState.disable)
        setName("")
        setDescription(null)
        setImage(null)
    }

    fun prepareAndSavePlaylist(image: String?){
        savePlaylist(makePlaylistForView(image))
    }

    private fun makePlaylistForView(image: String?): PlaylistForView{
        return PlaylistForView(
            id = null,
            name = nameLiveData.value!!,
            description = descriptionLiveData.value,
            imagePath = image,
            tracksIDList = emptyList(),
            tracksCount = 0
        )
    }

    private fun savePlaylist(playlistForView: PlaylistForView) {
        viewModelScope.launch {
            savePlaylistUseCase.execute(mapper.playlistForViewToPlaylistMap(playlistForView))
        }
    }
}