package com.example.playlistmaker.musicLibrary.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.TracksLibraryFragmentBinding
import com.example.playlistmaker.musicLibrary.presentation.adapter.SavedTrackClickListener
import com.example.playlistmaker.musicLibrary.presentation.adapter.SavedTracksRecyclerAdapter
import com.example.playlistmaker.musicLibrary.presentation.states.TracksLibraryState
import com.example.playlistmaker.musicLibrary.presentation.viewModel.TracksLibraryFragmentViewModel
import com.example.playlistmaker.player.presentation.ui.AudioPlayerActivity
import com.example.playlistmaker.search.presentation.model.TrackForView
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
                is TracksLibraryState.Content -> showContent(state.content)
                TracksLibraryState.Empty -> showEmpty()
            }
        }


    }

    override fun onResume() {
        super.onResume()

        viewModel.checkSavedTracks()
    }

    private fun showEmpty() {
        binding.emptyPlaylistsImage.isVisible = true
        binding.emptyPlaylistsText.isVisible = true
        binding.savedTracksRecycler.isVisible = false
        adapter?.clearList()
    }

    private fun showContent(content: List<TrackForView>) {
        binding.emptyPlaylistsImage.isVisible = false
        binding.emptyPlaylistsText.isVisible = false
        binding.savedTracksRecycler.isVisible = true
        adapter?.add(content)
    }

    override fun clickOnTrack(track: TrackForView) {
        viewModel.sendTrack(track)

        val intent = Intent(context, AudioPlayerActivity::class.java)
        startActivity(intent)
    }

    companion object {
        fun newInstance(): TracksLibraryFragment {
            return TracksLibraryFragment()
        }
    }
}