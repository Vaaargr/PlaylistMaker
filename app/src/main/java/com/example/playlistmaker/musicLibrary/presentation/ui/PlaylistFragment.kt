package com.example.playlistmaker.musicLibrary.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.App
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistFragmentBinding
import com.example.playlistmaker.musicLibrary.presentation.adapter.TracksInPlaylistAdapter
import com.example.playlistmaker.musicLibrary.presentation.adapter.clickListener.OnTrackInPlaylistClickListener
import com.example.playlistmaker.musicLibrary.presentation.entity.PlaylistForView
import com.example.playlistmaker.musicLibrary.presentation.states.TracksInPlaylistState
import com.example.playlistmaker.musicLibrary.presentation.viewModel.PlaylistFragmentViewModel
import com.example.playlistmaker.musicLibrary.presentation.viewModel.PlaylistLibraryViewModel
import com.example.playlistmaker.search.presentation.model.TrackForView
import com.example.playlistmaker.tools.Constans
import com.example.playlistmaker.tools.GlideClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Job
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistFragment : Fragment(), OnTrackInPlaylistClickListener {

    private var _binding: PlaylistFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModel<PlaylistFragmentViewModel>()

    private val imageLoader = GlideClient()

    private var trackAdapter: TracksInPlaylistAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlaylistFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trackAdapter = TracksInPlaylistAdapter(
            this,
            resources.getDimensionPixelOffset(R.dimen.small_corner_radius)
        )
        binding.tracksInPlaylistRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.tracksInPlaylistRecycler.adapter = trackAdapter

        val addMenuBehavior = BottomSheetBehavior.from(binding.addMenuBottomSheet)
            .apply { state = BottomSheetBehavior.STATE_HIDDEN }

        val shareButtonClickListener = OnClickListener {
            val state = viewModel.takeTracksList()
            when (state) {
                is TracksInPlaylistState.Content -> {
                    val tracksList = state.tracks
                    val playlist = viewModel.takePlaylist()
                    addMenuBehavior.state = BottomSheetBehavior.STATE_HIDDEN

                    var message =
                        "${playlist.name}\n${playlist.description}\n${playlist.tracksCount} ${
                            definePlural(playlist.tracksCount)
                        }"

                    tracksList.forEachIndexed { index, trackForView ->
                        message += "\n${index + 1}. ${trackForView.artistName} - ${trackForView.trackName} (${
                            SimpleDateFormat(
                                "mm.ss",
                                Locale.getDefault()
                            ).format(trackForView.trackTimeMillis)
                        })"
                    }

                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, message)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK
                    requireContext().startActivity(intent)
                }

                TracksInPlaylistState.Empty -> {
                    addMenuBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.nothing_to_share),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        binding.addMenuShareButton.setOnClickListener(shareButtonClickListener)
        binding.playlistShare.setOnClickListener(shareButtonClickListener)



        addMenuBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        addMenuOpen(true)
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        addMenuOpen(false)
                    }

                    else -> {}

                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        viewModel.observeTracksInPlaylistLD().observe(viewLifecycleOwner) { state ->
            when (state) {
                is TracksInPlaylistState.Content -> {
                    showTracks(true)
                    trackAdapter!!.add(state.tracks)

                    var duration = 0L
                    state.tracks.forEach { duration += it.trackTimeMillis }
                    val start = SimpleDateFormat("mm", Locale.getDefault()).format(duration)
                    val end = getDuration(duration)
                    val result = "$start $end"
                    binding.tracksTime.text = result
                }

                TracksInPlaylistState.Empty -> {
                    showTracks(false)
                    val text = "0 ${getString(R.string.minutes_match)}"
                    binding.tracksTime.text = text
                }
            }

        }

        viewModel.observePlaylistLD().observe(viewLifecycleOwner) { playlist ->
            if (playlist != null) {
                displayPlaylist(playlist)
                viewModel.checkTracksInPlaylist(playlist.id!!)
            } else {
                findNavController().navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }

            })

        binding.playlistBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.addMenuButton.setOnClickListener {
            addMenuBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.playlistOverlay.setOnClickListener {
            addMenuBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.addMenuEditButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong(Constans.PLAYLIST.value, viewModel.getPlaylistId())
            findNavController().navigate(
                R.id.action_playlistFragment_to_editPlaylistFragment,
                bundle
            )
        }

        binding.addMenuDeleteButton.setOnClickListener {
            addMenuBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogButtons)
                .setTitle(R.string.delete_playlist)
                .setMessage(R.string.want_to_delete_a_playlist)
                .setPositiveButton(R.string.yes) { _, _ ->
                    lifecycleScope.launch {
                        viewModel.deletePlaylist()

                        findNavController().navigateUp()
                    }
                }
                .setNegativeButton(R.string.no) { _, _ -> }
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        val playlistID = requireArguments().getLong(Constans.PLAYLIST.value)
        if (playlistID > -1) {
            viewModel.getPlaylist(playlistID)
        }
    }

    private fun displayPlaylist(playlist: PlaylistForView) {
        imageLoader.loadImage(
            requireContext(),
            playlist.imagePath,
            resources.getDimensionPixelOffset(R.dimen.small_corner_radius),
            binding.playlistImage
        )

        imageLoader.loadImage(
            requireContext(),
            playlist.imagePath,
            resources.getDimensionPixelOffset(R.dimen.small_corner_radius),
            binding.addMenuPlaylistImage
        )

        binding.playlistName.text = playlist.name
        binding.playlistDescription.text = playlist.description
        val countText = "${playlist.tracksCount} ${definePlural(playlist.tracksCount)}"
        binding.tracksInPlaylistCount.text = countText

        binding.addMenuPlaylistName.text = playlist.name
        binding.addMenuTracksCount.text = countText
    }

    private fun showTracks(flag: Boolean) {
        binding.tracksInPlaylistRecycler.isVisible = flag
        binding.playlistEmptyPlaylistsText.isVisible = !flag
        binding.playlistEmptyTracksImage.isVisible = !flag
    }

    private fun addMenuOpen(flag: Boolean) {
        binding.playlistOverlay.isVisible = flag
        binding.playlistOverlay.isClickable = flag
    }

    override fun onClick(track: TrackForView) {
        val bundle = Bundle()
        bundle.putLong(Constans.TRACK.value, track.trackId)
        findNavController().navigate(R.id.action_playlistFragment_to_audioPlayerFragment, bundle)
    }

    override fun onLongClick(track: TrackForView) {
        MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogButtons)
            .setTitle(R.string.delete_track)
            .setMessage(R.string.delete_track_body)

            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.deleteTrack(trackID = track.trackId, trackIsLiked = track.isLiked)
                viewModel.checkTracksInPlaylist(requireArguments().getLong(Constans.PLAYLIST.value))
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .show()
    }

    private fun getDuration(input: Long): String {
        val num = ((input / 1000) / 60).toInt()
        return requireContext().getString(
            when (num) {
                0 -> R.string.minutes_match
                1 -> R.string.minute_match
                in 2..4 -> R.string.minutes_unmatch
                in 5..20 -> R.string.minutes_match
                else -> {
                    val symbol = num.rem(10)
                    when (symbol) {
                        1 -> R.string.minutes_unmatch_a
                        in 2..4 -> R.string.minutes_unmatch
                        else -> R.string.minute_match
                    }
                }
            }
        )
    }

    private fun definePlural(number: Int): String {
        return requireContext().getString(
            when (number) {
                0 -> R.string.tracks_match_b
                1 -> R.string.track_match
                in 2..4 -> R.string.tracks_match_a
                in 5..20 -> R.string.tracks_match_b
                else -> {
                    val symbol = number % 10
                    when (symbol) {
                        1 -> R.string.tracks_unmatch
                        in 2..4 -> R.string.tracks_match_a
                        else -> R.string.tracks_match_a
                    }
                }
            }
        )
    }
}