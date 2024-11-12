package com.example.newsapp.presentation.OnBoarding

import androidx.annotation.DrawableRes

data class Page(
    val title:String,
    val description:String,
    @DrawableRes val page:Int
)
