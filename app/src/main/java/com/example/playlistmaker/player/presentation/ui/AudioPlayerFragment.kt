package com.example.playlistmaker.player.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.AudioPlayerFragmentBinding
import com.example.playlistmaker.player.presentation.adapter.PlaylistsPlayerRecyclerAdapter
import com.example.playlistmaker.player.presentation.states.SaveTrackInPlaylistState
import com.example.playlistmaker.tools.GlideClient
import com.example.playlistmaker.player.presentation.viewModel.PlayerViewModel
import com.example.playlistmaker.search.presentation.model.TrackForView
import com.example.playlistmaker.tools.Constans
import com.example.playlistmaker.tools.Formatter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel

class AudioPlayerFragment : Fragment() {
    private var _binding: AudioPlayerFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<PlayerViewModel>()
    private val imageLoader = GlideClient()
    private var adapter: PlaylistsPlayerRecyclerAdapter? = null
    private var trackFlag = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AudioPlayerFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val trackID = requireArguments().getLong(Constans.TRACK.value)
        if (trackID > -1L) {
            if (!trackFlag){
                viewModel.loadTrack(trackID)
                trackFlag = true
            }
        } else {
            findNavController().navigateUp()
        }

        viewModel.observeTrack().observe(viewLifecycleOwner) {
            displayTrack(it)
        }

        val behavior = BottomSheetBehavior.from(binding.standardBottomSheet)
            .apply { state = BottomSheetBehavior.STATE_HIDDEN }


        adapter =
            PlaylistsPlayerRecyclerAdapter(
                resources.getDimensionPixelOffset(R.dimen.small_corner_radius),
                requireContext(),
                viewModel
            )

        binding.playlistsRecyclerPlayer.layoutManager = LinearLayoutManager(requireContext())
        binding.playlistsRecyclerPlayer.adapter = adapter

        viewModel.getPlayerState().observe(viewLifecycleOwner) { state ->
            changeButton(state.buttonState)
        }

        viewModel.getTimer().observe(viewLifecycleOwner) { time ->
            binding.timerText.text = Formatter.timeFormat(time)
        }


        viewModel.observePlaylistState().observe(viewLifecycleOwner) { state ->
            adapter!!.add(state)
        }

        viewModel.observeSaveTrackInPlaylist().observe(viewLifecycleOwner) { state ->

            when (state) {
                is SaveTrackInPlaylistState.Failure -> {
                    val text = "${getString(R.string.track_already_added)} ${state.playlistName}"
                    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
                }

                is SaveTrackInPlaylistState.Success -> {
                    val text = "${getString(R.string.added_to_playlist)} ${state.playlistName}"
                    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
                    behavior.state =
                        BottomSheetBehavior.STATE_HIDDEN
                }
            }
        }

        viewModel.checkPlaylists()

        binding.playButton.setOnClickListener {
            viewModel.playStopButtonClick()
        }

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.likeButton.setOnClickListener {
            viewModel.likeButtonClick()
        }

        binding.addToPlaylistButton.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.newPlaylistButtonPlayer.setOnClickListener {
            findNavController().navigate(R.id.action_audioPlayerFragment_to_editPlaylistFragment)
        }

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.overlay.isVisible = true
                        viewModel.checkPlaylists()
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.isVisible = false
                    }

                    else -> {
                        binding.overlay.isVisible = true
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.checkAndDeleteTrack()
                    findNavController().navigateUp()
                }
            })

    }

    private fun displayTrack(track: TrackForView) {
        imageLoader.loadImage(
            requireContext(),
            track.artworkUrl512,
            resources.getDimensionPixelSize(R.dimen.big_corner_radius),
            binding.albumImage
        )
        isLiked(track.isLiked)
        with(binding) {
            trackName.text = track.trackName
            artistName.text = track.artistName
            duration.text = track.trackTimeString
            if (track.collectionName.isNullOrEmpty()) {
                albumText.isVisible = false
                album.isVisible = false
            } else album.text = track.collectionName
            year.text = track.releaseDate.subSequence(0, 4)
            genre.text = track.primaryGenreName
            country.text = track.country
        }
    }

    private fun isLiked(isLiked: Boolean) {
        when (isLiked) {
            true -> binding.likeButton.setImageResource(R.drawable.saved_heart)
            false -> binding.likeButton.setImageResource(R.drawable.unsaved_heart)
        }
    }

    private fun changeButton(isPlayed: Boolean) {
        binding.playButton.setImageResource(if (isPlayed) R.drawable.pause else R.drawable.play)
    }

    override fun onPause() {
        super.onPause()
        viewModel.playerPause()
    }
}