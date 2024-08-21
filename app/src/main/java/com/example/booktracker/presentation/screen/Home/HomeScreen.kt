package com.example.booktracker.presentation.screen.Home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.domain.model.AuthViewModel

@Composable
fun HomeScreen(toSignin: (() -> Unit), authViewModel: AuthViewModel = hiltViewModel(), showBottomBar: Boolean ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
        Text(text = "Logged In!")
        Button(onClick = {
            authViewModel.onSignOut()
            toSignin()
        }) {
            Text(text = "Sign Out")
        }
        if(showBottomBar) {
            Text(text = "Bottom bar shown")
        }
    }
}