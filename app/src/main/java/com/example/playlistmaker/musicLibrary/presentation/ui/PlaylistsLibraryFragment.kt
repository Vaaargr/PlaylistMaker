package com.example.playlistmaker.musicLibrary.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.PlaylistsLibraryFragmentBinding
import com.example.playlistmaker.musicLibrary.presentation.viewModel.EmptyPlaylistsFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PlaylistsLibraryFragment : Fragment() {

    private lateinit var binding: PlaylistsLibraryFragmentBinding
    private val viewModel by activityViewModel<EmptyPlaylistsFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlaylistsLibraryFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): PlaylistsLibraryFragment {
            return PlaylistsLibraryFragment()
        }
    }
}