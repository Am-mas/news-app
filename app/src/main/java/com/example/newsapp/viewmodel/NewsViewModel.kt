package com.example.newsapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Article
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch
import okhttp3.Response
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    private val _newsArticles = MutableLiveData<List<Article>>()
    val newsArticles: LiveData<List<Article>> = _newsArticles

    private val _category = MutableLiveData<String>()
    val category: LiveData<String> = _category

    private val _country = MutableLiveData<String>()
    val country: LiveData<String> = _country

    private val _sources = MutableLiveData<String>()
    val sources: LiveData<String> = _sources

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    fun getTopHeadlines(
        country: String,
        apiKey:String
    ){
        viewModelScope.launch {
            try {
                val response = repository.getTopNews(country,apiKey)
                if (response.isSuccessful){
                    _newsArticles.postValue(response.body()?.articles?: emptyList())
                } else {
                    Log.e(TAG, response.errorBody().toString())
                }
            } catch (e:Exception) {
                Log.e(TAG, "Cannot load because ${e.message}")
            }
        }
    }

    fun getTopHeadlinesByCategory(
        category: String,
        apiKey: String
    ) {
        viewModelScope.launch {
            try {
                val response = repository.getTopNewsByCategory(category, apiKey)
                if (response.isSuccessful){
                    _newsArticles.postValue(response.body()?.articles?: emptyList())
                } else {
                    Log.e(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e(TAG, "Cannot load because:${e.message}")
            }
        }
    }

    fun getTopHeadlinesBySources(
        sources: String,
        apiKey: String
    ) {
        viewModelScope.launch {
            try {
                val response = repository.getTopNewsBySource(sources,apiKey)
                if (response.isSuccessful) {
                    _newsArticles.postValue(response.body()?.articles?: emptyList())
                } else {
                    Log.e(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e(TAG,"Cannot load because: ${e.message}")
            }
        }
    }
    fun getTopHeadlinesBySearch(
        query: String,
        apiKey: String
    ){
        viewModelScope.launch {
            try {
                val response = repository.getTopNewsByQuery(query, apiKey)
                if (response.isSuccessful) {
                    _newsArticles.postValue(response.body()?.articles?: emptyList())
                } else {
                    Log.e(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e(TAG, "Cannot load because: ${e.message}")
            }
        }
    }

    fun setCategory(selected: String,apiKey: String) {
        _category.value = selected
        getTopHeadlinesByCategory(selected,apiKey)

    }

    fun setCountry(selected: String,apiKey: String) {
        _country.value = selected
        getTopHeadlines(selected,apiKey)
    }

    fun setSource(selected: String,apiKey: String) {
        _sources.value = selected
        getTopHeadlinesBySources(selected, apiKey)
    }
    fun setQuery(selected: String,apiKey: String) {
        _query.value = selected
        getTopHeadlinesBySearch(selected, apiKey)

    }
}