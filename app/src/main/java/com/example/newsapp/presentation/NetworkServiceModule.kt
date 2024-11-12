package com.example.newsapp.presentation

import com.example.newsapp.data.model.Constants
import com.example.newsapp.data.network.Everything
import com.example.newsapp.presentation.Everything.EverythingRepository
import com.example.newsapp.presentation.Everything.EverythingViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkServiceModule {

    @Provides
    @Singleton
    fun providesEverythingAPi(): Everything {
        return Retrofit.Builder()
            .baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Everything::class.java)
    }

    @Provides
    @Singleton
    fun provideEverythingRepo(everything: Everything) : EverythingRepository
    {
        return EverythingRepository(everything)
    }

}