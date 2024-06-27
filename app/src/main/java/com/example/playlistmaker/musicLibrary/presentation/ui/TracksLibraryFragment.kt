package com.example.playlistmaker.musicLibrary.presentation.ui

import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.TracksLibraryFragmentBinding
import com.example.playlistmaker.musicLibrary.presentation.adapter.clickListener.SavedTrackClickListener
import com.example.playlistmaker.musicLibrary.presentation.adapter.SavedTracksRecyclerAdapter
import com.example.playlistmaker.musicLibrary.presentation.states.TracksLibraryState
import com.example.playlistmaker.musicLibrary.presentation.viewModel.TracksLibraryFragmentViewModel
import com.example.playlistmaker.search.presentation.model.TrackForView
import com.example.playlistmaker.tools.Constans
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class TracksLibraryFragment : Fragment(), SavedTrackClickListener {

    private var _binding: TracksLibraryFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModel<TracksLibraryFragmentViewModel>()

    private var adapter: SavedTracksRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TracksLibraryFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SavedTracksRecyclerAdapter(
            this,
            resources.getDimensionPixelOffset(R.dimen.small_corner_radius)
        )

        binding.savedTracksRecycler.layoutManager = LinearLayoutManager(context)
        binding.savedTracksRecycler.adapter = adapter

        viewModel.getStateLiveData().observe(viewLifecycleOwner) { state ->
            when (state) {
                is TracksLibraryState.Content -> {
                    releaseState(flag = true)
                    adapter?.add(state.content)
                }

                TracksLibraryState.Empty -> {
                    releaseState(flag = false)
                    adapter?.clearList()
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        viewModel.checkSavedTracks()
    }

    private fun releaseState(flag: Boolean) {
        binding.emptyPlaylistsImage.isVisible = !flag
        binding.emptyPlaylistsText.isVisible = !flag
        binding.savedTracksRecycler.isVisible = flag
    }

    override fun clickOnTrack(track: TrackForView) {
        val bundle = Bundle()
        bundle.putLong(Constans.TRACK.value, track.trackId)
        findNavController().navigate(R.id.action_musicLibraryFragment_to_audioPlayerFragment, bundle)
    }

    companion object {
        fun newInstance(): TracksLibraryFragment {
            return TracksLibraryFragment()
        }
    }
}