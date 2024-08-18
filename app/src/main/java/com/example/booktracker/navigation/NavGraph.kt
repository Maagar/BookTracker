package com.example.booktracker.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.booktracker.domain.model.AuthViewModel
import com.example.booktracker.presentation.screen.Home.HomeScreen
import com.example.booktracker.presentation.screen.SignIn.SignInScreen
import com.example.booktracker.presentation.screen.SignUp.SignUpScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val isSignedIn by authViewModel.isSignedIn.collectAsState(initial = false)
    authViewModel.isUserSignedIn()

    NavHost(navController = navController, startDestination = if(isSignedIn) Screen.Home else Screen.SignIn, modifier = Modifier.fillMaxSize()) {
        composable<Screen.SignIn> {
            SignInScreen(toSignUpScreen = {
                navController.navigate(
                    Screen.SignUp
                )
            },
                toHomeScreen = {
                    navController.navigate(
                        Screen.Home
                    )
                })
        }
        composable<Screen.SignUp> {
            SignUpScreen(toSignInScreen = {
                navController.navigate(
                    Screen.SignIn
                )
            })
        }
        composable<Screen.Home> {
            HomeScreen()
        }
    }
}