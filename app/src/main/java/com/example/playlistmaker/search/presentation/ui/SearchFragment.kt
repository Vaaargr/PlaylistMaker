package com.example.playlistmaker.search.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.SearchFragmentBinding
import com.example.playlistmaker.search.presentation.model.TrackForView
import com.example.playlistmaker.player.presentation.ui.AudioPlayerFragment
import com.example.playlistmaker.search.presentation.model.ResponseResult
import com.example.playlistmaker.search.presentation.state.SearchActivityState
import com.example.playlistmaker.search.presentation.viewModel.SearchingViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment(), TrackClickListener {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SearchingViewModel>()

    private var responseAdapter: SearchRecyclerAdapter? = null
    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        binding.queryInput.setText("")
        viewModel.clearRequest()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkHistory()

        responseAdapter = SearchRecyclerAdapter(
            this,
            resources.getDimensionPixelOffset(R.dimen.small_corner_radius)
        )

        viewModel.getState().observe(viewLifecycleOwner) { state ->
            implementState(state)
        }

        with(binding) {
            searchRecycler.layoutManager = LinearLayoutManager(context)
            searchRecycler.adapter = responseAdapter

            queryInput.setText(viewModel.getRequest())

            clearButton.setOnClickListener {
                queryInput.setText("")
                viewModel.clearRequest()
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(queryInput.windowToken, 0)
                queryInput.clearFocus()
                responseAdapter?.clearList()
                showResult(SearchActivityState.Response(ResponseResult.GOOD, emptyList()))
                binding.clearButton.isVisible = false
            }

            updateButton.setOnClickListener {
                search(IMMEDIATELY_SEARCH_MILLIS)
            }

            queryInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (viewModel.changeRequest(s.toString())) {
                        binding.clearButton.isVisible = true
                        hideHistory()
                        search(SEARCH_DELAY_MILLIS)
                    } else if (s.isNullOrEmpty()) {
                        binding.clearButton.isVisible = false
                        viewModel.cancelSearch()
                        viewModel.checkHistory()
                    } else {
                        binding.clearButton.isVisible = true
                    }
                }
            })

            queryInput.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    if (viewModel.getRequest().isNullOrEmpty()) {
                        viewModel.checkHistory()
                    }
                } else {
                    hideHistory()
                }
            }

            queryInput.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search(IMMEDIATELY_SEARCH_MILLIS)
                }
                false
            }

            clearSearchHistoryButton.setOnClickListener {
                viewModel.clearHistory()
                hideHistory()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isClickAllowed = true
    }

    private fun search(searchDelay: Long) {
        showProgressBar(true)
        viewModel.search(searchDelay)
    }

    private fun implementState(state: SearchActivityState) {
        when (state) {
            is SearchActivityState.History -> {
                showHistory(state.trackList)
            }

            is SearchActivityState.Response -> {
                hideHistory()
                showResult(state)
            }
        }
    }


    private fun showResult(response: SearchActivityState.Response) {
        with(binding) {
            when (response.responseResult) {
                ResponseResult.GOOD -> {
                    responseAdapter?.add(response.trackList)
                    setErrorsVisibility(
                        searchRecyclerVisibility = true,
                        errorImageVisibility = false,
                        errorTextVisibility = false,
                        updateButtonVisibility = false
                    )
                }

                ResponseResult.EMPTY -> {
                    errorText.text = getString(R.string.empty_search)
                    errorImage.setImageResource(R.drawable.empty_search)
                    setErrorsVisibility(
                        searchRecyclerVisibility = false,
                        errorImageVisibility = true,
                        errorTextVisibility = true,
                        updateButtonVisibility = false
                    )
                }

                ResponseResult.ERROR -> {
                    errorText.text = getString(R.string.internet_error)
                    errorImage.setImageResource(R.drawable.internet_error)
                    setErrorsVisibility(
                        searchRecyclerVisibility = false,
                        errorImageVisibility = true,
                        errorTextVisibility = true,
                        updateButtonVisibility = true
                    )
                }
            }
        }
    }

    private fun setErrorsVisibility(
        searchRecyclerVisibility: Boolean,
        errorImageVisibility: Boolean,
        errorTextVisibility: Boolean,
        updateButtonVisibility: Boolean
    ) {
        showProgressBar(false)
        with(binding) {
            searchRecycler.isVisible = searchRecyclerVisibility
            errorImage.isVisible = errorImageVisibility
            errorText.isVisible = errorTextVisibility
            updateButton.isVisible = updateButtonVisibility
        }
    }

    private fun showHistory(history: List<TrackForView>) {
        setSearchHistoryVisibility(true)
        responseAdapter?.add(history)
    }

    private fun hideHistory() {
        setSearchHistoryVisibility(false)
    }

    private fun setSearchHistoryVisibility(input: Boolean) {
        with(binding) {
            searchHistoryText.isVisible = input
            clearSearchHistoryButton.isVisible = input
            if (!input) {
                responseAdapter?.clearList()
            }
        }
    }

    override fun clickOnTrack(track: TrackForView) {
        if (clickDebounce()) {
            viewModel.saveTrack(track)
            viewModel.sendTrack(track)

            findNavController().navigate(R.id.action_searchFragment_to_audioPlayerFragment)
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        return current
    }

    private fun showProgressBar(input: Boolean) {
        with(binding) {
            progressBar.isVisible = input
            searchRecycler.isVisible = false
            errorImage.isVisible = false
            errorText.isVisible = false
            updateButton.isVisible = false

        }
    }

    companion object {
        private const val SEARCH_DELAY_MILLIS = 2000L
        private const val IMMEDIATELY_SEARCH_MILLIS = 0L
        private const val CLICK_DELAY_MILLIS = 1000L
    }
}