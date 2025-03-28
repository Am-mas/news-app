package com.example.newsapp.model

import java.util.Date

data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val publishedAt: String,
    val urlToImage: String,
    val content: String

)
