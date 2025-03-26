package com.example.newsapp.di

import com.example.newsapp.service.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideNewsApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}