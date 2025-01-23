package com.example.booktracker.presentation.ui

sealed class UserState {
    object Loading: UserState()
    object SignedIn: UserState()
    object NotSignedIn: UserState()
}