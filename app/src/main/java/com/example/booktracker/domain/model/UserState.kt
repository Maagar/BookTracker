package com.example.booktracker.domain.model

sealed class UserState {
    object Loading: UserState()
    object SignedIn: UserState()
    object NotSignedIn: UserState()
}