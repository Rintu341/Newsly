package com.example.newsapp.data.model

sealed class AuthStatus() {
    object Unauthenticated : AuthStatus()
    object Authenticated : AuthStatus()
    object Loading : AuthStatus()
    data class  Error(val message: String) : AuthStatus()
}