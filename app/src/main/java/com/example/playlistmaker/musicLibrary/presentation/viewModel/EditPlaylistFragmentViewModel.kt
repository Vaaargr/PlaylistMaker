package com.example.playlistmaker.musicLibrary.presentation.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.musicLibrary.domain.api.interactors.EditPlaylistInteractor
import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView
import com.example.playlistmaker.musicLibrary.presentation.mapper.PlaylistForViewMapper
import com.example.playlistmaker.musicLibrary.presentation.states.EditPlaylistState
import com.example.playlistmaker.musicLibrary.presentation.states.NewPlaylistFragmentState
import kotlinx.coroutines.launch

class EditPlaylistFragmentViewModel(
    private val editPlaylistInteractor: EditPlaylistInteractor,
    private val mapper: PlaylistForViewMapper
) : ViewModel() {

    private val editStateLiveData = MutableLiveData<EditPlaylistState>()
    private val buttonStateLiveData =
        MutableLiveData<NewPlaylistFragmentState>(NewPlaylistFragmentState.Disable)
    private val nameLiveData = MutableLiveData<String>("")
    private val descriptionLiveData = MutableLiveData<String?>()
    private val imageLiveData = MutableLiveData<Uri?>()

    fun observeEditState(): LiveData<EditPlaylistState> = editStateLiveData

    fun observeButtonState(): LiveData<NewPlaylistFragmentState> = buttonStateLiveData

    fun observeImageLD(): LiveData<Uri?> = imageLiveData

    fun getUri(): Uri? {
        return imageLiveData.value
    }

    fun getName(): String? {
        return nameLiveData.value
    }

    private fun setEditState(state: EditPlaylistState) {
        editStateLiveData.postValue(state)
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
            setState(NewPlaylistFragmentState.Disable)
        } else {
            setState(NewPlaylistFragmentState.Enable)
            setName(name)
        }
    }

    fun checkData(): Boolean {
        return (nameLiveData.value.isNullOrEmpty() && descriptionLiveData.value.isNullOrEmpty() && imageLiveData.value == null)
    }

    fun clearDate() {
        setState(NewPlaylistFragmentState.Disable)
        setName("")
        setDescription(null)
        setImage(null)
        setEditState(EditPlaylistState.New)
    }

    fun prepareAndSavePlaylist(image: String?) {
        savePlaylist(makePlaylistForView(image))
    }

    private fun makePlaylistForView(image: String?): PlaylistForView {
        return PlaylistForView(
            id = null,
            name = nameLiveData.value!!,
            description = descriptionLiveData.value,
            imagePath = image,
            tracksCount = 0
        )
    }

    private fun savePlaylist(playlistForView: PlaylistForView) {
        viewModelScope.launch {
            editPlaylistInteractor.savePlaylist(mapper.playlistForViewToPlaylistMap(playlistForView))
        }
    }

    fun checkEditState(id: Long?) {
        if (id == null || id == -1L) {
            setEditState(EditPlaylistState.New)
        } else {
            viewModelScope.launch {
                val playlist =
                    mapper.playlistToPlaylistForView(editPlaylistInteractor.loadPlaylist(id))
                setEditState(EditPlaylistState.Edit(playlist))
                setName(playlist.name)
            }
        }
    }

    fun updatePlaylist(playlist: PlaylistForView){
        viewModelScope.launch {
            editPlaylistInteractor.updatePlaylist(mapper.playlistForViewToPlaylistMap(playlist))
        }
    }
}