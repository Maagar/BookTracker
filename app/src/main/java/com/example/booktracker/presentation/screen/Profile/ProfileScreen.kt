package com.example.booktracker.presentation.screen.Profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.R
import com.example.booktracker.presentation.screen.Profile.component.BooksPieChart
import com.example.booktracker.presentation.screen.Profile.component.ProfileHeader
import com.example.booktracker.presentation.screen.Profile.component.StatCard
import com.example.booktracker.presentation.screen.Profile.component.StatCards
import com.example.booktracker.presentation.ui.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    toSignIn: (() -> Unit),
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val profileData by profileViewModel.profileData.collectAsState()
    val stats by profileViewModel.stats.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileHeader(profileData)

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        stats?.let { BooksPieChart(it.ownership_read_percentage) }

        StatCards(title = "Statistics") {
            StatCard(title = "Read volumes", stat = "${stats?.read_volumes_count}")
            StatCard(title = "Owned volumes", stat = "${stats?.owned_volumes_count}")
            StatCard(title = "Followed series", stat = "${stats?.followed_series_count}")
        }



        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                authViewModel.onSignOut()
                toSignIn()
            }) {
            Text(text = stringResource(R.string.sign_out))
        }
    }
}