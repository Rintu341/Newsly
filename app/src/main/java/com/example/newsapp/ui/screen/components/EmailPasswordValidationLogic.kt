package com.example.newsapp.ui.screen.components

// Function to check specific password errors and return appropriate messages
fun getPasswordError(password: String): String? {
    return when {
        password.length < 8 -> "Password must be at least 8 characters"
        !password.any { it.isUpperCase() } -> "Password must contain at least one uppercase letter"
        !password.any { it.isLowerCase() } -> "Password must contain at least one lowercase letter"
        !password.any { it.isDigit() } -> "Password must contain at least one digit"
        !password.any { "!@#\$%^&*()-_=+<>?".contains(it) } -> "Password must contain at least one special character"
        else -> null // No error, password is valid
    }
}
fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

