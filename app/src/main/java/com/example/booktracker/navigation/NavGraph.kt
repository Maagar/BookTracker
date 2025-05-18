package com.example.booktracker.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.booktracker.presentation.ui.viewmodel.AuthViewModel
import com.example.booktracker.presentation.component.BottomNavigationBar
import com.example.booktracker.presentation.screen.Discover.DiscoverScreen
import com.example.booktracker.presentation.screen.Library.LibraryScreen
import com.example.booktracker.presentation.screen.Loading.LoadingScreen
import com.example.booktracker.presentation.screen.Profile.ProfileScreen
import com.example.booktracker.presentation.screen.Series.SeriesScreen
import com.example.booktracker.presentation.screen.SignIn.SignInScreen
import com.example.booktracker.presentation.screen.SignUp.SignUpScreen
import com.example.booktracker.presentation.screen.Volume.VolumeScreen
import com.example.booktracker.presentation.ui.viewmodel.SeriesViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    seriesViewModel: SeriesViewModel = hiltViewModel()
) {
    val userState by authViewModel.userState.collectAsState()
    val startDestination = getStartDestination(userState)

    fun navigateTo(screen: Screen, popUpToScreen: Screen? = null) {
        navController.navigate(screen::class.qualifiedName ?: return) {
            popUpToScreen?.let {
                popUpTo(it::class.qualifiedName ?: return@let) { inclusive = true }
            }
            launchSingleTop = true
        }
    }

    Scaffold(
        bottomBar = {
            if (getScaffoldVisibility(navController)) {
                BottomNavigationBar(
                    navController = navController,
                    currentScreen = getCurrentScreen(navController)
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<Screen.Loading> { LoadingScreen() }
            composable<Screen.SignIn> {
                SignInScreen(
                    toSignUpScreen = { navigateTo(Screen.SignUp, Screen.SignIn) },
                    toHomeScreen = { navigateTo(Screen.Library, Screen.SignIn) }
                )
            }
            composable<Screen.SignUp> {
                SignUpScreen(
                    toSignInScreen = { navigateTo(Screen.SignIn, Screen.SignUp) },
                    toHomeScreen = { navigateTo(Screen.Library, Screen.SignUp) }
                )
            }
            composable<Screen.Library> {
                LibraryScreen(
                    seriesViewModel,
                    toSeriesScreen = { navigateTo(Screen.Series) },
                    toVolumeScreen = { navigateTo(Screen.Volume) }
                )
            }
            composable<Screen.Discover> {
                DiscoverScreen(seriesViewModel, toSeriesScreen = { navigateTo(Screen.Series) })
            }
            composable<Screen.Profile> {
                ProfileScreen(toSignIn = {
                    navController.navigate(Screen.SignIn) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                })
            }
            composable<Screen.Series>(
                enterTransition = { slideInTransition() },
                exitTransition = { fadeOut() },
                popExitTransition = { slideOutTransition() },
                popEnterTransition = { fadeIn() }
            ) {
                CompositionLocalProvider(com.example.booktracker.utils.LocalSeriesViewModel provides seriesViewModel) {
                    SeriesScreen(seriesViewModel, toVolumeScreen = { navigateTo(Screen.Volume) })
                }
            }
            composable<Screen.Volume>(
                enterTransition = { slideInTransition() },
                exitTransition = { slideOutTransition() },
                popExitTransition = { slideOutTransition() }
            ) {
                CompositionLocalProvider(com.example.booktracker.utils.LocalSeriesViewModel provides seriesViewModel) {
                    VolumeScreen(seriesViewModel)
                }
            }
        }
    }
}
