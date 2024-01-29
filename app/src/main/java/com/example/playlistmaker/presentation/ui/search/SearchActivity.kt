package com.example.playlistmaker.presentation.ui.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.enums.SearchResponseVariants
import com.example.playlistmaker.domain.consumer.Consumer
import com.example.playlistmaker.domain.entity.SearchResponse
import com.example.playlistmaker.presentation.mappers.SearchResponseMapper
import com.example.playlistmaker.presentation.mappers.TrackViewMapper
import com.example.playlistmaker.presentation.model.TrackForView
import com.example.playlistmaker.presentation.ui.audioPlayer.AudioPlayerActivity
import com.example.playlistmaker.tools.Creator


class SearchActivity : AppCompatActivity(), SearchRecyclerAdapter.TrackClickListener {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var responseAdapter: SearchRecyclerAdapter
    private lateinit var historyAdapter: SearchRecyclerAdapter

    private val trackExchangeInteract = Creator.getTrackExchangeInteractImpl()
    private val searchTrackUseCase = Creator.getSearchTrackUseCase()
    private val searchHistory = Creator.getSearchHistoryInteractor()

    private val mainHandler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { search() }
    private var isClickAllowed = true
    private var savedText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        responseAdapter = SearchRecyclerAdapter(this, ROUNDING_RADIUS)
        historyAdapter = SearchRecyclerAdapter(this, ROUNDING_RADIUS)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.toolbar_back_arrow)
        }

        with(binding) {
            searchRecycler.layoutManager = LinearLayoutManager(this@SearchActivity)

            clearButton.setOnClickListener {
                queryInput.setText("")
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(queryInput.windowToken, 0)
                queryInput.clearFocus()
                responseAdapter.clearList()
                showResult(SearchResponseVariants.GOOD_RESPONSE)
            }

            updateButton.setOnClickListener {
                search()
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
                    savedText = s.toString()
                    binding.clearButton.isVisible = !s.isNullOrEmpty()
                    setSearchHistoryVisibility(false)
                    searchRecycler.adapter = responseAdapter
                    if (savedText.isNotEmpty()) {
                        searchDebounce(SEARCH_DELAY_MILLIS)
                    }
                }
            })

            queryInput.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    val history = searchHistory.getHistory()
                    if (history.isNotEmpty()) {
                        searchRecycler.adapter = historyAdapter
                        setSearchHistoryVisibility(true)
                    } else {
                        setSearchHistoryVisibility(false)
                    }
                } else {
                    setSearchHistoryVisibility(false)
                }
            }

            queryInput.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchDebounce(IMMEDIATELY_SEARCH_MILLIS)
                }
                false
            }

            clearSearchHistoryButton.setOnClickListener {
                searchHistory.clearHistory()
                setSearchHistoryVisibility(false)
            }
        }
    }

    private fun search() {
        responseAdapter.clearList()
        showProgressBar(true)
        searchTrackUseCase.execute(savedText, object : Consumer {
            override fun consume(searchResponse: SearchResponse) {
                mainHandler.post {
                    val response = SearchResponseMapper.map(searchResponse)
                    if (response.resultCode == 200) {
                        if (response.results.isNotEmpty()) {
                            binding.searchRecycler.adapter = responseAdapter
                            responseAdapter.add(response.results)
                            showResult(SearchResponseVariants.GOOD_RESPONSE)

                        } else {
                            showResult(SearchResponseVariants.EMPTY_RESPONSE)
                        }
                    } else {
                        showResult(SearchResponseVariants.RESPONSE_ERROR)
                    }
                }
            }
        })
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        if (savedInstanceState != null) {
            savedText = savedInstanceState.getString(
                SEARCH_ACTIVITY_EDIT_TEXT,""
            )
        }
        binding.queryInput.setText(savedText)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (savedText.isNotEmpty()) {
            outState.putString(SEARCH_ACTIVITY_EDIT_TEXT, savedText)
        }
    }

    private fun showResult(result: SearchResponseVariants) {
        with(binding) {
            when (result.code) {
                SearchResponseVariants.GOOD_RESPONSE.code -> {
                    setErrorsVisibility(
                        searchRecyclerVisibility = true,
                        errorImageVisibility = false,
                        errorTextVisibility = false,
                        updateButtonVisibility = false
                    )
                }

                SearchResponseVariants.EMPTY_RESPONSE.code -> {
                    errorText.text = getString(R.string.empty_search)
                    errorImage.setImageResource(R.drawable.empty_search)
                    setErrorsVisibility(
                        searchRecyclerVisibility = false,
                        errorImageVisibility = true,
                        errorTextVisibility = true,
                        updateButtonVisibility = false
                    )
                }

                SearchResponseVariants.RESPONSE_ERROR.code -> {
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

    private fun setSearchHistoryVisibility(input: Boolean) {
        with(binding) {
            searchHistoryText.isVisible = input
            clearSearchHistoryButton.isVisible = input
            if (input) {
                historyAdapter.add(TrackViewMapper.trackArrayToTrackToViewArrayMap(searchHistory.getHistory()))
            } else {
                historyAdapter.clearList()
            }
        }
    }

    override fun onClick(track: TrackForView) {
        if (clickDebounce()) {
            searchHistory.saveTrack(TrackViewMapper.trackForViewToTrackMap(track))
            trackExchangeInteract.sendTrack(TrackViewMapper.trackForViewToTrackMap(track))

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
        private const val SEARCH_ACTIVITY_EDIT_TEXT = "Edit text"
    }
}