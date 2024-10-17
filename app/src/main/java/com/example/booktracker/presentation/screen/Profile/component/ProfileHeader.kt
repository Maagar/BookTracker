package com.example.booktracker.presentation.screen.Profile.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booktracker.R
import com.example.booktracker.data.model.ProfileData
import com.example.ui.theme.AppTypography

@Composable
fun ProfileHeader(profileData: ProfileData?) {
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {

        AsyncImage(
            model = profileData?.avatar_url,
            error = painterResource(R.drawable.avatar_placeholder),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(76.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape)
        )
        Text(
            text = profileData?.username ?: stringResource(R.string.username),
            style = AppTypography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )
    }
}