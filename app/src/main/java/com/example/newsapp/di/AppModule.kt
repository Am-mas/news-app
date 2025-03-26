package com.example.newsapp.di
import android.app.Application
import androidx.room.Room
import com.example.newsapp.dao.NewsDao
import com.example.newsapp.database.NewsDatabase
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.service.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @Provides
    @Singleton
    fun provideNewsDatabase(application: Application): NewsDatabase {
        return Room.databaseBuilder(application, NewsDatabase::class.java, "news_db").build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao = newsDatabase.newsDao()

    @Provides
    @Singleton
    fun provideNewsRepository(apiService: ApiService, newsDao: NewsDao): NewsRepository = NewsRepository(apiService, newsDao)
}