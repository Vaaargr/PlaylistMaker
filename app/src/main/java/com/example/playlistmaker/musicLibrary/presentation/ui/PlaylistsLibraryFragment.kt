package com.example.playlistmaker.musicLibrary.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistsLibraryFragmentBinding
import com.example.playlistmaker.musicLibrary.presentation.adapter.PlaylistsRecyclerAdapter
import com.example.playlistmaker.musicLibrary.presentation.states.PlaylistLibraryState
import com.example.playlistmaker.musicLibrary.presentation.viewModel.NewPlaylistFragmentViewModel
import com.example.playlistmaker.musicLibrary.presentation.viewModel.PlaylistLibraryViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PlaylistsLibraryFragment : Fragment() {

    private var _binding: PlaylistsLibraryFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModel<PlaylistLibraryViewModel>()

    private var adapter: PlaylistsRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlaylistsLibraryFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newPlaylistButton.setOnClickListener {
            findNavController().navigate(R.id.action_musicLibraryFragment_to_newPlaylistFragment)
        }

        adapter = PlaylistsRecyclerAdapter(resources.getDimensionPixelOffset(R.dimen.big_corner_radius), requireContext())

        binding.playlistsRecycler.layoutManager = GridLayoutManager(context, 2)
        binding.playlistsRecycler.adapter = adapter

        viewModel.observeState().observe(viewLifecycleOwner){ state ->
            when(state){
                is PlaylistLibraryState.Content -> {
                    stateHandle(true)
                    adapter?.add(state.content)
                }
                PlaylistLibraryState.Empty -> stateHandle(false)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.checkSavedPlaylists()
    }

    private fun stateHandle(state: Boolean){
        binding.emptyPlaylistsImage.isVisible = !state
        binding.emptyPlaylistsText.isVisible = !state
        binding.playlistsRecycler.isVisible = state
    }


    companion object {
        fun newInstance(): PlaylistsLibraryFragment {
            return PlaylistsLibraryFragment()
        }
    }
}