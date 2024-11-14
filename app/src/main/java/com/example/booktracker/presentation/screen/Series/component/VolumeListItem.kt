package com.example.booktracker.presentation.screen.Series.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.booktracker.data.model.VolumeToInsert
import com.example.booktracker.data.model.VolumeToUpdate
import com.example.ui.theme.AppTypography

@Composable
fun VolumeListItem(
    volume: Volume,
    onItemClick: (Volume) -> Unit = {},
    onUserVolumeInsert: (VolumeToInsert) -> Unit,
    onUserVolumeUpdate: (VolumeToUpdate) -> Unit,
    onUserVolumeDelete: (Int) -> Unit = {},
    onOwnedVolumeClick: () -> Unit,
    onReadClick: () -> Unit,
    isSingleVolume: Boolean
) {

    ListItem(
        modifier = Modifier.let { modifier ->
            if (!isSingleVolume) modifier.clickable { onItemClick(volume) } else modifier
        },
        headlineContent = { Text(text = volume.title, style = AppTypography.titleMedium) },
        leadingContent = {
            AsyncImage(
                model = volume.cover_url,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp)),
                fallback = painterResource(R.drawable.no_image_placeholder)
            )
        },
        trailingContent = {
            Row {
                ListItemIcon(
                    R.drawable.library_books,
                    if (volume.owned) {
                        MaterialTheme.colorScheme.tertiary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
                    if (volume.owned) {
                        MaterialTheme.colorScheme.onTertiary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                ) {
                    if (!volume.owned && volume.times_read == 0) {
                        onUserVolumeInsert(
                            VolumeToInsert(volume.id, 0, true)
                        )
                    } else if (!volume.owned && volume.times_read > 0 && volume.user_volume_id != null) {
                        onUserVolumeUpdate(
                            VolumeToUpdate(
                                volume.user_volume_id,
                                volume.id,
                                volume.times_read,
                                true
                            )
                        )
                    } else {
                        onOwnedVolumeClick()
                    }
                }
                ListItemIcon(
                    R.drawable.check,
                    if (volume.times_read > 0) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
                    if (volume.times_read > 0) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                ) {
                    if (!volume.owned && volume.times_read == 0) {
                        onUserVolumeInsert(
                            VolumeToInsert(volume.id, 1, false)
                        )
                    } else if (volume.owned && volume.times_read == 0 && volume.user_volume_id != null) {
                        onUserVolumeUpdate(
                            VolumeToUpdate(volume.user_volume_id, volume.id, 1, volume.owned)
                        )
                    } else {
                        onReadClick()
                    }
                }
            }
        }
    )
}
