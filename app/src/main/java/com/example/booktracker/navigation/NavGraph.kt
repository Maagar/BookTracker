package com.example.booktracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.booktracker.presentation.screen.SignIn.SignInScreen
import com.example.booktracker.presentation.screen.SignUp.SignUpScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.SignIn
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable<Screen.SignIn> {
            SignInScreen(toSignUpScreen = {
                navController.navigate(
                    Screen.SignUp
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
    }
}