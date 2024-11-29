package com.example.newsapp.data.model

import android.net.Uri

data class User(
    var imageUri:Uri? = null,
    var name:String? = null,
    var email:String? = null,
)
