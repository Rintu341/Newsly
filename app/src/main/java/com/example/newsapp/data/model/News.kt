package com.example.newsapp.data.model

data class NewsResponse(
    var articles: List<Article>,
    val status: String,
    val totalResults: Int
)