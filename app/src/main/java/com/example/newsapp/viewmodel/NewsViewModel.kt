package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Article
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch
import java.util.Locale.IsoCountryCode
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    suspend fun getTopHeadlines(
        country: String,
        apiKey:String
    ){
        viewModelScope.launch {
            try {
                val response = repository.getTopNews(country,apiKey)
                if (response.isSuccessful){
                    response.body()?.articles
                } else {
                    response.message()
                }
            } catch (e:Exception) {

            }
        }

    }
}