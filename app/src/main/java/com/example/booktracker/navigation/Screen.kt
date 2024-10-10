package com.example.booktracker.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val showScaffold: Boolean = false) {
    @Serializable
    data object SignIn: Screen()
    @Serializable
    data object SignUp: Screen()
    @Serializable
    data object Library: Screen(showScaffold = true)
    @Serializable
    data object Discover: Screen(showScaffold = true)
    @Serializable
    data object Profile: Screen(showScaffold = true)
    @Serializable
    data object Loading: Screen()
    @Serializable
    data object Series: Screen()
    @Serializable
    data object Volume: Screen()
}