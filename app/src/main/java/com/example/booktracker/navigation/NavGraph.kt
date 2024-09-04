package com.example.booktracker.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.booktracker.ui.viewmodel.AuthViewModel
import com.example.booktracker.domain.model.UserState
import com.example.booktracker.presentation.component.BottomNavigationBar
import com.example.booktracker.presentation.screen.Discover.DiscoverScreen
import com.example.booktracker.presentation.screen.Library.LibraryScreen
import com.example.booktracker.presentation.screen.Loading.LoadingScreen
import com.example.booktracker.presentation.screen.Profile.ProfileScreen
import com.example.booktracker.presentation.screen.SignIn.SignInScreen
import com.example.booktracker.presentation.screen.SignUp.SignUpScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val userState by authViewModel.userState.collectAsState()

    val startDestination = when (userState) {
        UserState.Loading -> Screen.Loading
        UserState.NotSignedIn -> Screen.SignIn
        UserState.SignedIn -> Screen.Library
    }
    val toSignUpScreen = {
        navController.navigate(
            Screen.SignUp
        ) {
            popUpTo(Screen.SignIn) { inclusive = true }
        }
    }

    val toSignInScreen = {
        navController.navigate(
            Screen.SignIn
        ) {

        }
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = currentBackStackEntry?.destination?.route?.let {
        when (it) {
            Screen.SignIn::class.qualifiedName -> Screen.SignIn
            Screen.SignUp::class.qualifiedName -> Screen.SignUp
            Screen.Library::class.qualifiedName -> Screen.Library
            Screen.Discover::class.qualifiedName -> Screen.Discover
            Screen.Profile::class.qualifiedName -> Screen.Profile
            Screen.Loading::class.qualifiedName -> Screen.Loading
            else -> null
        }
    }
    val showScaffold = currentScreen?.showScaffold ?: false

    Scaffold(
        bottomBar = {
            if(showScaffold) {
                BottomNavigationBar(navController = navController, currentScreen)
            }
        }
    ) {
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<Screen.Loading> {
                LoadingScreen()
            }
            composable<Screen.SignIn> {
                SignInScreen(toSignUpScreen,
                    toHomeScreen = {
                        navController.navigate(
                            Screen.Library
                        ) {
                            popUpTo(Screen.SignIn) { inclusive = true }
                        }
                    })
            }
            composable<Screen.SignUp> {
                SignUpScreen(toSignInScreen = {
                    navController.navigate(
                        Screen.SignIn
                    ) {
                        popUpTo(Screen.SignUp) { inclusive = true }
                    }
                })
            }
            composable<Screen.Library> {
                LibraryScreen()
            }
            composable<Screen.Discover> {
                DiscoverScreen()
            }
            composable<Screen.Profile> {
                ProfileScreen(toSignInScreen)
            }
        }
    }
    

}