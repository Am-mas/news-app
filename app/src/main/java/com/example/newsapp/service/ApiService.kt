package com.example.newsapp.service

import com.example.newsapp.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey:String
    ): Response<NewsResponse>

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlinesBySource(
        @Query("source") source: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlinesByCategory(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>
    @GET("/v2/top-headlines")
    suspend fun getTopHeadlinesBySearch(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String
    ) : Response<NewsResponse>
}