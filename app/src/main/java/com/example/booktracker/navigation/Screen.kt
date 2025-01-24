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

    companion object {
        fun fromRoute(route: String): Screen? {
            return when (route) {
                SignIn::class.qualifiedName -> SignIn
                SignUp::class.qualifiedName -> SignUp
                Library::class.qualifiedName -> Library
                Discover::class.qualifiedName -> Discover
                Profile::class.qualifiedName -> Profile
                Loading::class.qualifiedName -> Loading
                Series::class.qualifiedName -> Series
                Volume::class.qualifiedName -> Volume
                else -> null
            }
        }
    }
}