package com.example.booktracker.presentation.screen.Library.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booktracker.R
import com.example.booktracker.data.model.UpcomingVolume

@Composable
fun UpcomingListItem(
    upcomingVolume: UpcomingVolume,
    toSeriesScreen: () -> Unit,
    toVolumeScreen: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .clickable { toVolumeScreen() }
            .padding(8.dp)
            .fillMaxWidth(),
        leadingContent = {
            AsyncImage(
                model = upcomingVolume.cover_url,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp)),
                placeholder = painterResource(R.drawable.no_image_placeholder),
                fallback = painterResource(R.drawable.no_image_placeholder)
            )
        },
        headlineContent = {
            Column {
                Text(
                    upcomingVolume.series_title + " >",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable { toSeriesScreen() }
                        .padding(end = 4.dp, top = 4.dp, bottom = 4.dp)
                )

                Text(upcomingVolume.title, style = MaterialTheme.typography.titleMedium)
            }

        },
        supportingContent = {
            Text(
                stringResource(R.string.volume) + " " + "${upcomingVolume.volume_number}",
                style = MaterialTheme.typography.labelLarge
            )
        },
        trailingContent = {
            Row {
                Text(
                    "${upcomingVolume.daysRemaining()}" + " " + stringResource(R.string.days),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    )
}