package com.example.booktracker.presentation.screen.Profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.R
import com.example.booktracker.presentation.screen.Profile.component.ProfileHeader
import com.example.booktracker.ui.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    toSignin: (() -> Unit),
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val profileData by profileViewModel.profileData.collectAsState()

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileHeader(profileData)
        Button(onClick = {
            authViewModel.onSignOut()
            toSignin()
        }) {
            Text(text = stringResource(R.string.sign_out))
        }
    }
}