package com.example.booktracker.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.booktracker.presentation.ui.UserState

@Composable
fun getStartDestination(userState: UserState): Screen {
    return when (userState) {
        UserState.Loading -> Screen.Loading
        UserState.NotSignedIn -> Screen.SignIn
        UserState.SignedIn -> Screen.Library
    }
}

@Composable
fun getScaffoldVisibility(navController: NavHostController): Boolean {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = currentBackStackEntry?.destination?.route?.let { Screen.fromRoute(it) }
    return currentScreen?.showScaffold ?: false
}

@Composable
fun getCurrentScreen(navController: NavHostController): Screen {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    return Screen.fromRoute(currentBackStackEntry?.destination?.route ?: "") ?: Screen.Library
}

fun slideInTransition(): EnterTransition = slideInVertically(
    initialOffsetY = { 3000 },
    animationSpec = tween(500)
)

fun slideOutTransition(): ExitTransition = slideOutVertically(
    targetOffsetY = { 3000 }, animationSpec = tween(500)
)