package com.example.newsapp.presentation.Everything

import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.network.Everything
import javax.inject.Inject

class EverythingRepository @Inject constructor(
    private val everything: Everything
)
{
    suspend fun getNews(query:String,apikey:String): NewsResponse {
       return everything.getNews(query,apikey)
    }
}