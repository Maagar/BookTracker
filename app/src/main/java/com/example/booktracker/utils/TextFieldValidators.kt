package com.example.booktracker.utils

fun validateEmail(email: String): String? {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()
    return if (email.isEmpty()) {
        "Email cannot be empty."
    } else if (!email.matches(emailRegex)) {
        "Invalid email format."
    } else null
}

fun validatePassword(password: String): String? {
    return if (password.isEmpty()) {
        "Password cannot be empty."
    } else if (password.length < 4) {
        "Password must be at least 4 characters long."
    } else null
}

fun validateUsername(username: String): String? {
    return if (username.isEmpty()) {
        "Username cannot be empty."
    } else if (username.length < 3) {
        "Username must be at least 3 characters long."
    } else null
}