package com.example.playlistmaker.activitys

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
import com.example.playlistmaker.enums.ConstantsKey
import com.example.playlistmaker.R
import com.example.playlistmaker.SearchHistory
import com.example.playlistmaker.Tools
import com.example.playlistmaker.adapters.SearchRecyclerAdapter
import com.example.playlistmaker.api.ITunesSearchApi
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.enums.HistoryDictionary
import com.example.playlistmaker.enums.SearchResponseVariants
import com.example.playlistmaker.models.ITunesResponse
import com.example.playlistmaker.models.Track
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity(), SearchRecyclerAdapter.TrackClickListener {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchHistory: SearchHistory
    private lateinit var responseAdapter: SearchRecyclerAdapter
    private lateinit var historyAdapter: SearchRecyclerAdapter
    private val mainHandler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { search() }
    private var isClickAllowed = true

    private var savedText = ""
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val iTunesService = retrofit.create(ITunesSearchApi::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val roundingRadius = Tools().dpToPx(2F, this)

        searchHistory = SearchHistory(
            getSharedPreferences(
                HistoryDictionary.SHARED_PREFS_SEARCH_HISTORY_NAME.value,
                MODE_PRIVATE
            )
        )

        responseAdapter = SearchRecyclerAdapter(this, roundingRadius)
        historyAdapter = SearchRecyclerAdapter(this, roundingRadius)

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
                        searchDebounce(SEARCH_DELAY)
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
                    searchDebounce(IMMEDIATELY_SEARCH)
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
        iTunesService.search(savedText).enqueue(object : Callback<ITunesResponse> {
            override fun onResponse(
                call: Call<ITunesResponse>,
                response: Response<ITunesResponse>
            ) {
                if (response.code() == 200) {
                    if (response.body()?.results?.isNotEmpty() == true) {
                        binding.searchRecycler.adapter = responseAdapter
                        responseAdapter.add(response.body()!!.results)
                        showResult(SearchResponseVariants.GOOD_RESPONSE)
                    } else {
                        showResult(SearchResponseVariants.EMPTY_RESPONSE)
                    }
                } else {
                    showResult(SearchResponseVariants.RESPONSE_ERROR)
                }
            }

            override fun onFailure(call: Call<ITunesResponse>, t: Throwable) {
                showResult(SearchResponseVariants.RESPONSE_ERROR)
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
                ConstantsKey.SEARCH_ACTIVITY_EDIT_TEXT.value,
                ""
            )
        }
        binding.queryInput.setText(savedText)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (savedText.isNotEmpty()) {
            outState.putString(ConstantsKey.SEARCH_ACTIVITY_EDIT_TEXT.value, savedText)
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
                historyAdapter.add(searchHistory.getHistory())
            } else {
                historyAdapter.clearList()
            }
        }
    }

    override fun onClick(track: Track) {
        if (clickDebounce()) {
            searchHistory.saveTrack(track)
            val intent = Intent(this, AudioPlayerActivity::class.java)
            intent.putExtra(ConstantsKey.TRACK.value, track)
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
            mainHandler.postDelayed({ isClickAllowed = true }, CLICK_DELAY)
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
        private const val SEARCH_DELAY = 2000L
        private const val IMMEDIATELY_SEARCH = 0L
        private const val CLICK_DELAY = 1000L
    }
}