package com.example.booktracker.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.booktracker.R
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToInsert
import com.example.booktracker.data.model.VolumeToUpdate
import com.example.booktracker.utils.LocalSeriesViewModel

@Composable
fun VolumeStatusButtons(
    volume: Volume,
    onOwnedVolumeClick: () -> Unit,
    onReadClick: () -> Unit,
    equalWeight: Boolean = false
) {
    val seriesViewModel = LocalSeriesViewModel.current

    Row(
        modifier = if (equalWeight) Modifier.fillMaxWidth() else Modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
    ) {
        ListItemIcon(
            painterResource = R.drawable.library_books,
            backgroundColor = if (volume.owned) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surfaceVariant,
            iconColor = if (volume.owned) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSurfaceVariant,
            onClick = {
                if (!volume.owned && volume.times_read == 0) {
                    seriesViewModel.onUserVolumeInsert(
                        VolumeToInsert(volume.id, 0, true)
                    )
                } else if (!volume.owned && volume.times_read > 0 && volume.user_volume_id != null) {
                    seriesViewModel.onUserVolumeUpdate(
                        VolumeToUpdate(
                            volume.user_volume_id,
                            volume.id,
                            volume.times_read,
                            true,
                            volume.rating
                        )
                    )
                } else {
                    onOwnedVolumeClick()
                }
            },
            modifier = if (equalWeight) Modifier.weight(1f).padding(8.dp) else Modifier
        )
        ListItemIcon(
            painterResource = R.drawable.check,
            backgroundColor = if (volume.times_read > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
            iconColor = if (volume.times_read > 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
            onClick = {
                if (!volume.owned && volume.times_read == 0) {
                    seriesViewModel.onUserVolumeInsert(
                        VolumeToInsert(volume.id, 1, false)
                    )
                } else if (volume.owned && volume.times_read == 0 && volume.user_volume_id != null) {
                    seriesViewModel.onUserVolumeUpdate(
                        VolumeToUpdate(volume.user_volume_id, volume.id, 1, volume.owned, volume.rating)
                    )
                } else {
                    onReadClick()
                }
            },
            modifier = if (equalWeight) Modifier.weight(1f).padding(8.dp) else Modifier
        )

    }
}