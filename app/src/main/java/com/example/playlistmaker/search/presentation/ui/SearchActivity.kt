package com.example.playlistmaker.search.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.search.presentation.model.TrackForView
import com.example.playlistmaker.player.presentation.ui.AudioPlayerActivity
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.search.presentation.model.ResponseResult
import com.example.playlistmaker.search.presentation.state.SearchActivityState
import com.example.playlistmaker.search.presentation.viewModel.SearchingViewModel


class SearchActivity : AppCompatActivity(), TrackClickListener {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchingViewModel

    private var responseAdapter: SearchRecyclerAdapter? = null
    private val mainHandler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { search() }
    private var isClickAllowed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        responseAdapter = SearchRecyclerAdapter(this, ROUNDING_RADIUS)

        viewModel = ViewModelProvider(
            this, SearchingViewModel.factory(
                Creator.getSendTrackUseCase(),
                Creator.getSearchTrackUseCase(),
                Creator.getSearchHistoryInteractor()
            )
        )[SearchingViewModel::class.java]

        viewModel.getState().observe(this) { state ->
            implementState(state)
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.toolbar_back_arrow)
        }

        with(binding) {
            searchRecycler.layoutManager = LinearLayoutManager(this@SearchActivity)
            searchRecycler.adapter = responseAdapter

            queryInput.setText(viewModel.getRequest())

            clearButton.setOnClickListener {
                queryInput.setText("")
                viewModel.clearRequest()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(queryInput.windowToken, 0)
                queryInput.clearFocus()
                responseAdapter?.clearList()
                showResult(SearchActivityState.Response(ResponseResult.GOOD, emptyList()))
                binding.clearButton.isVisible = false
            }

            updateButton.setOnClickListener {
                searchDebounce(IMMEDIATELY_SEARCH_MILLIS)
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
                        searchDebounce(SEARCH_DELAY_MILLIS)
                    } else if (s.isNullOrEmpty()) {
                        binding.clearButton.isVisible = false
                        mainHandler.removeCallbacks(searchRunnable)
                        viewModel.checkHistory()
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
                    searchDebounce(IMMEDIATELY_SEARCH_MILLIS)
                }
                false
            }

            clearSearchHistoryButton.setOnClickListener {
                viewModel.clearHistory()
                hideHistory()
            }
        }
    }

    private fun search() {
        showProgressBar(true)
        viewModel.search()
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

            val intent = Intent(this, AudioPlayerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun searchDebounce(delay: Long) {
        mainHandler.removeCallbacks(searchRunnable)
        mainHandler.postDelayed(searchRunnable, delay)
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            mainHandler.postDelayed({ isClickAllowed = true }, CLICK_DELAY_MILLIS)
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
        private const val ROUNDING_RADIUS = 2F
    }
}