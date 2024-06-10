package com.example.playlistmaker.musicLibrary.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.EmptyPlaylistsFragmentBinding
import com.example.playlistmaker.musicLibrary.presentation.viewModel.EmptyPlaylistsFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class EmptyPlaylistsFragment : Fragment() {

    companion object {
        fun newInstance(): EmptyPlaylistsFragment {
            return EmptyPlaylistsFragment()
        }
    }

    private lateinit var binding: EmptyPlaylistsFragmentBinding
    private val viewModel by activityViewModel<EmptyPlaylistsFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EmptyPlaylistsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}