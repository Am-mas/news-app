package com.example.newsapp.repository

import com.example.newsapp.dao.NewsDao
import com.example.newsapp.service.ApiService
import javax.inject.Inject

class NewsRepository @Inject constructor( private val apiService: ApiService, private val newsDao: NewsDao) {
    suspend fun getTopNews(country: String, apiKey:String) = apiService.getTopHeadlines(country,apiKey)
    suspend fun getTopNewsByCategory(category: String, apiKey: String) = apiService.getTopHeadlinesByCategory(category,apiKey)
    suspend fun getTopNewsBySource( source: String, apiKey: String) = apiService.getTopHeadlinesBySource(source, apiKey)
    suspend fun getTopNewsByQuery(q: String, apiKey: String) = apiService.getTopHeadlinesBySearch(q, apiKey)
}