package com.example.newsapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.entity.ArticlesEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles:List<ArticlesEntity>)

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<ArticlesEntity>

    @Query("DELETE FROM articles")
    suspend fun clearArticles()

}