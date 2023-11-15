package com.example.playlistmaker.activitys

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.Constants
import com.example.playlistmaker.R
import com.example.playlistmaker.adapters.SearchRecyclerAdapter
import com.example.playlistmaker.api.ITunesSearchApi
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.models.ITunesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private var savedText = ""
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val iTunesService = retrofit.create(ITunesSearchApi::class.java)
    private val adapter = SearchRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.toolbar_back_arrow)
        }

        with(binding) {
            clearButton.setOnClickListener {
                queryInput.setText("")
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(queryInput.windowToken, 0)
                queryInput.clearFocus()
                adapter.clearList()
                hideEmptySearch()
                hideInternetError()
            }

            updateButton.setOnClickListener {
                makeQuery()
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
                    binding.clearButton.visibility =
                        if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                }
            })

            searchRecycler.layoutManager = LinearLayoutManager(this@SearchActivity)
            searchRecycler.adapter = adapter

            queryInput.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                   makeQuery()
                }
                false
            }
        }
    }

    private fun search() {
        iTunesService.search(savedText).enqueue(object : Callback<ITunesResponse> {
            override fun onResponse(
                call: Call<ITunesResponse>,
                response: Response<ITunesResponse>
            ) {
                if (response.code() == 200) {
                    if (response.body()?.results?.isNotEmpty() == true) {
                        adapter.add(response.body()!!.results)
                    } else{
                        showEmptySearch()
                    }
                } else {
                    showInternetError()
                }
            }

            override fun onFailure(call: Call<ITunesResponse>, t: Throwable) {
                showInternetError()
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
                Constants.SEARCH_ACTIVITY_EDIT_TEXT.value,
                ""
            )
        }
        binding.queryInput.setText(savedText)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (savedText.isNotEmpty()) {
            outState.putString(Constants.SEARCH_ACTIVITY_EDIT_TEXT.value, savedText)
        }
    }

    private fun makeQuery(){
        hideEmptySearch()
        hideInternetError()
        search()
    }

    private fun showEmptySearch(){
        with(binding){
            searchRecycler.visibility = View.GONE
            emptySearchImage.visibility = View.VISIBLE
            emptySearchText.visibility = View.VISIBLE
        }
    }

    private fun hideEmptySearch(){
        with(binding){
            searchRecycler.visibility = View.VISIBLE
            emptySearchText.visibility = View.GONE
            emptySearchImage.visibility = View.GONE
        }
    }

    private fun showInternetError(){
        with(binding){
            searchRecycler.visibility = View.GONE
            internetErrorImage.visibility = View.VISIBLE
            internetErrorText.visibility = View.VISIBLE
            updateButton.visibility = View.VISIBLE
        }
    }

    private fun hideInternetError(){
        with(binding){
            searchRecycler.visibility = View.VISIBLE
            internetErrorImage.visibility = View.GONE
            internetErrorText.visibility = View.GONE
            updateButton.visibility = View.GONE
        }
    }
}