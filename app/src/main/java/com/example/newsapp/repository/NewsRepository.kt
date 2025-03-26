package com.example.newsapp.repository

import com.example.newsapp.dao.NewsDao
import com.example.newsapp.service.ApiService
import javax.inject.Inject

class NewsRepository @Inject constructor( private val apiService: ApiService, private val newsDao: NewsDao) {
    suspend fun getTopNews(country: String, apiKey:String) = apiService.getTopHeadlines(country,apiKey)
}