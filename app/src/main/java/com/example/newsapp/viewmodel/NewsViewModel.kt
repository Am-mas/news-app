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
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    private val _newsArticles = MutableLiveData<List<Article>>()
    val newsArticles: LiveData<List<Article>> = _newsArticles

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
                    Log.e(TAG,"${response.errorBody().toString()}")
                }
            } catch (e:Exception) {
                Log.e(TAG, "Cannot load because ${e.message}")
            }
        }

    }
}