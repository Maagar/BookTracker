package com.example.booktracker.presentation.screen.Volume.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.booktracker.R
import com.example.ui.theme.AppTypography

@Composable
fun VolumeSynopsis(synopsis: String?) {
    Card(modifier = Modifier.padding(8.dp)) {
        Text(
            if (synopsis.isNullOrEmpty()) {
                stringResource(R.string.no_synopsis_available)
            } else synopsis,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            style = AppTypography.bodyMedium,
        )
    }
}