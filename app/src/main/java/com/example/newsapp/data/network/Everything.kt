package com.example.newsapp.data.network

import com.example.newsapp.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface Everything {

    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String,
        @Query("from") from: String
    ) : NewsResponse
}