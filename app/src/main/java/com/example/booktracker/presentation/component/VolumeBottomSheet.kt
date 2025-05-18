package com.example.booktracker.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.booktracker.R
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToUpdate
import com.example.booktracker.utils.LocalSeriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumeBottomSheet(
    showOwnedBottomSheet: Boolean,
    showReadBottomSheet: Boolean,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    bottomSheetVolume: Volume?,
) {
    val seriesViewModel = LocalSeriesViewModel.current

    if (showOwnedBottomSheet || showReadBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            containerColor = MaterialTheme.colorScheme.background,
            sheetState = sheetState
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.titleSmall,
                text = stringResource(R.string.mark_as)
            )

            if (showOwnedBottomSheet) {
                ListItem(
                    headlineContent = {
                        Text(
                            stringResource(R.string.not_owned),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    modifier = Modifier.clickable {
                        bottomSheetVolume?.let { volume ->
                            volume.user_volume_id?.let { userVolumeId ->
                                if (volume.times_read > 0) {
                                    seriesViewModel.onUserVolumeUpdate(
                                        VolumeToUpdate(
                                            userVolumeId,
                                            volume.id,
                                            volume.times_read,
                                            false,
                                            volume.rating
                                        )
                                    )
                                } else {
                                    seriesViewModel.onUserVolumeDelete(userVolumeId, volume.id)
                                }
                            }
                        }
                        onDismiss()
                    }
                )
            } else if (showReadBottomSheet) {
                ListItem(
                    headlineContent = {
                        Text(
                            stringResource(R.string.not_read),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    modifier = Modifier.clickable {
                        bottomSheetVolume?.let { volume ->
                            volume.user_volume_id?.let { userVolumeId ->
                                if (volume.owned) {
                                    seriesViewModel.onUserVolumeUpdate(
                                        VolumeToUpdate(
                                            userVolumeId,
                                            volume.id,
                                            0,
                                            volume.owned,
                                            volume.rating
                                        )
                                    )
                                } else {
                                    seriesViewModel.onUserVolumeDelete(userVolumeId, volume.id)
                                }
                            }
                        }
                        onDismiss()
                    }
                )

                HorizontalDivider()

                ListItem(
                    headlineContent = {
                        Text(
                            stringResource(R.string.reread),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    modifier = Modifier.clickable {
                        bottomSheetVolume?.let { volume ->
                            volume.user_volume_id?.let { userVolumeId ->
                                seriesViewModel.onUserVolumeUpdate(
                                    VolumeToUpdate(
                                        userVolumeId,
                                        volume.id,
                                        volume.times_read + 1,
                                        volume.owned,
                                        volume.rating
                                    )
                                )
                            }
                        }
                        onDismiss()
                    }
                )
            }
        }
    }
}
