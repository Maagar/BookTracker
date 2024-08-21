package com.example.booktracker.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val showBottomBar: Boolean = false) {
    @Serializable
    data object SignIn: Screen()
    @Serializable
    data object SignUp: Screen()
    @Serializable
    data object Home: Screen(showBottomBar = true)
    @Serializable
    data object Loading: Screen()
}