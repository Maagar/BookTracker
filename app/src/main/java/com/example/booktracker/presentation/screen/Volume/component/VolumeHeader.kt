package com.example.booktracker.presentation.screen.Volume.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booktracker.R
import com.example.booktracker.data.model.Volume
import com.example.ui.theme.AppTypography

@Composable
fun VolumeHeader(volume: Volume) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = volume.cover_url,
            contentDescription = null,
            modifier = Modifier
                .weight(0.4f)
                .size(width = 150.dp, height = 200.dp),
            fallback = painterResource(R.drawable.no_image_placeholder)
        )
        Column(
            modifier = Modifier
                .weight(0.6f)
                .padding(start = 8.dp)
        ) {
            Text(text = volume.title, style = AppTypography.titleLarge)
            Row {
                Icon(
                    painterResource(R.drawable.calendar_month),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(text = "${volume.release_date ?: stringResource(R.string.release_date_not_yet_announced)}")
            }
            Row {
                Icon(
                    painterResource(R.drawable.book),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(text = "${volume.read_date ?: stringResource(R.string.not_read_yet)}")
            }
        }
    }
}