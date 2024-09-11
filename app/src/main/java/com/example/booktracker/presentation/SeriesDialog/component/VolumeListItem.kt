package com.example.booktracker.presentation.SeriesDialog.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booktracker.R
import com.example.booktracker.data.model.Volume
import com.example.ui.theme.AppTypography

@Composable
fun VolumeListItem(
    volume: Volume,
    onItemClick: (Volume) -> Unit = {},
    onFollowSeries: () -> Unit = {},
    onUnfollowSeries: () -> Unit = {}
) {
    ListItem(
        modifier = Modifier.clickable { onItemClick(volume) },
        headlineContent = { Text(text = volume.title, style = AppTypography.titleMedium) },
        leadingContent = {
            AsyncImage(
                model = volume.cover_url,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        },
        trailingContent = {
            val iconSize = 30.dp
            IconButton(
                onClick = {}, modifier = Modifier
                    .size(72.dp)
                    .padding(8.dp)
            ) {
                if (volume.userVolumes.times_read > 0 && volume.userVolumes.owned) Icon(
                    painterResource(R.drawable.check_circle),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(iconSize)
                )
                else if(volume.userVolumes.times_read == 0 && volume.userVolumes.owned) Icon(
                    painterResource(R.drawable.check_circle),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(iconSize)
                )
                else if (volume.userVolumes.times_read > 0 && !volume.userVolumes.owned) Icon(
                        painterResource(R.drawable.check_circle),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(iconSize)
                    )
                else Icon(
                    painterResource(R.drawable.check_circle),
                    contentDescription = null,
                    modifier = Modifier.size(iconSize)
                )

            }
        }
    )
}