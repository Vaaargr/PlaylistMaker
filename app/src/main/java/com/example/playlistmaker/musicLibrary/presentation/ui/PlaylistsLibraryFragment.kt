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
import com.example.playlistmaker.musicLibrary.presentation.adapter.clickListener.PlaylistClickListener
import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView
import com.example.playlistmaker.musicLibrary.presentation.states.PlaylistLibraryState
import com.example.playlistmaker.musicLibrary.presentation.viewModel.PlaylistLibraryViewModel
import com.example.playlistmaker.tools.Constans
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PlaylistsLibraryFragment : Fragment(), PlaylistClickListener {

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
            findNavController().navigate(R.id.action_musicLibraryFragment_to_editPlaylistFragment)
        }

        adapter = PlaylistsRecyclerAdapter(
            resources.getDimensionPixelOffset(R.dimen.big_corner_radius),
            requireContext(),
            this
        )

        binding.playlistsRecycler.layoutManager = GridLayoutManager(context, 2)
        binding.playlistsRecycler.adapter = adapter

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
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

    private fun stateHandle(state: Boolean) {
        binding.emptyPlaylistsImage.isVisible = !state
        binding.emptyPlaylistsText.isVisible = !state
        binding.playlistsRecycler.isVisible = state
    }

    override fun clickOnPlaylist(playlist: PlaylistForView) {
        val bundle = Bundle()
        bundle.putLong(Constans.PLAYLIST.value, playlist.id!!)
        findNavController().navigate(R.id.action_musicLibraryFragment_to_playlistFragment, bundle)
    }

    companion object {
        fun newInstance(): PlaylistsLibraryFragment {
            return PlaylistsLibraryFragment()
        }
    }
}