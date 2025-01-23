package com.example.booktracker.utils

import android.content.Context
import com.example.booktracker.R

fun validateEmail(email: String, context: Context): String? {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()
    return if (email.isEmpty()) {
        context.getString(R.string.email_empty)
    } else if (!email.matches(emailRegex)) {
        context.getString(R.string.invalid_email)
    } else null
}

fun validatePassword(password: String, context: Context): String? {
    return if (password.isEmpty()) {
        context.getString(R.string.password_empty)
    } else if (password.length < 4) {
        context.getString(R.string.short_password)
    } else null
}

fun validateUsername(username: String, context: Context): String? {
    return if (username.isEmpty()) {
        context.getString(R.string.username_empty)
    } else if (username.length < 3) {
        context.getString(R.string.username_short)
    } else null
}