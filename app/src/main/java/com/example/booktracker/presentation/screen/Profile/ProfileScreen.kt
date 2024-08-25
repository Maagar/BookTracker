package com.example.booktracker.presentation.screen.Profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.domain.model.AuthViewModel

@Composable
fun ProfileScreen(toSignin: (() -> Unit), authViewModel: AuthViewModel = hiltViewModel()) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profile screen")
        Button(onClick = {
            authViewModel.onSignOut()
            toSignin()
        }) {
            Text(text = "Sign Out")
        }
    }
}