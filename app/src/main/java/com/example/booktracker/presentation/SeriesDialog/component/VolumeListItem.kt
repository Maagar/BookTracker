package com.example.booktracker.presentation.SeriesDialog.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.booktracker.R
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToUpsert
import com.example.booktracker.ui.viewmodel.SeriesViewModel
import com.example.ui.theme.AppTypography

@Composable
fun VolumeListItem(
    volume: Volume,
    onItemClick: (Volume) -> Unit = {},
    onFollowSeries: () -> Unit = {},
    onUnfollowSeries: () -> Unit = {},
    seriesViewModel: SeriesViewModel = hiltViewModel()
) {
    var volumeStatus = getVolumeStatus(volume)
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
                onClick = {
                    if (volumeStatus == VolumeStatus.UNREAD_AND_UNOWNED || volumeStatus == VolumeStatus.UNREAD_AND_OWNED) {
                        val timesRead = if (volumeStatus == VolumeStatus.UNREAD_AND_UNOWNED) 0
                            else if (volumeStatus == VolumeStatus.UNREAD_AND_OWNED) 1
                            else volume.userVolumes.times_read
                        seriesViewModel.onVolumeStatusChange(
                            upsert = VolumeToUpsert(
                                id = volume.userVolumes.id,
                                volume_id = volume.id,
                                times_read = timesRead,
                                owned = true
                            )
                        ) { success ->
                            if (success) {
                                volumeStatus = VolumeStatus.UNREAD_AND_OWNED
                            }
                        }
                    }
                }, modifier = Modifier
                    .size(72.dp)
                    .padding(8.dp)
            ) {
                val iconTint = when (volumeStatus) {
                    VolumeStatus.READ_AND_OWNED -> MaterialTheme.colorScheme.primary
                    VolumeStatus.UNREAD_AND_OWNED -> MaterialTheme.colorScheme.secondary
                    VolumeStatus.READ_AND_UNOWNED -> MaterialTheme.colorScheme.tertiary
                    VolumeStatus.UNREAD_AND_UNOWNED -> LocalContentColor.current
                }
                Icon(
                    painterResource(R.drawable.check_circle),
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(iconSize)
                )

            }
        }
    )
}

enum class VolumeStatus {
    READ_AND_OWNED,
    UNREAD_AND_OWNED,
    READ_AND_UNOWNED,
    UNREAD_AND_UNOWNED
}

fun getVolumeStatus(volume: Volume): VolumeStatus {
    return when {
        volume.userVolumes.times_read > 0 && volume.userVolumes.owned -> VolumeStatus.READ_AND_OWNED
        volume.userVolumes.times_read == 0 && volume.userVolumes.owned -> VolumeStatus.UNREAD_AND_OWNED
        volume.userVolumes.times_read > 0 && !volume.userVolumes.owned -> VolumeStatus.READ_AND_UNOWNED
        else -> VolumeStatus.UNREAD_AND_UNOWNED
    }
}