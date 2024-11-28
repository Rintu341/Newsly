package com.example.newsapp.data.model

//@Parcelize
data class CardContent(
    var articleId:String? = "",
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val urlImage : String? = "",
    val url : String ? = "",
    val content : String? = ""
)
