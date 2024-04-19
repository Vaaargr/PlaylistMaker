package com.example.playlistmaker.musicLibrary.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.EmptyTracksFragmentBinding
import com.example.playlistmaker.musicLibrary.presentation.viewModel.EmptyTracksFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class EmptyTracksFragment : Fragment() {

    companion object {
        fun newInstance(): EmptyTracksFragment {
            return EmptyTracksFragment()
        }
    }

    private lateinit var binding: EmptyTracksFragmentBinding
    private val viewModel by activityViewModel<EmptyTracksFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EmptyTracksFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}