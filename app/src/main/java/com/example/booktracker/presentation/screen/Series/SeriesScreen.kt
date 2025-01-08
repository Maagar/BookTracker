package com.example.booktracker.presentation.screen.Series

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.booktracker.R
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToUpdate
import com.example.booktracker.presentation.screen.Series.component.AboutSeries
import com.example.booktracker.presentation.component.TabRow
import com.example.booktracker.presentation.screen.Series.component.SeriesHeader
import com.example.booktracker.presentation.screen.Series.component.VolumeListItem
import com.example.booktracker.presentation.component.SeriesProgressIndicator
import com.example.booktracker.ui.viewmodel.SeriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesScreen(
    seriesViewModel: SeriesViewModel,
    toVolumeScreen: () -> Unit
) {
    val series by seriesViewModel.series.collectAsState()
    val seriesInfo by seriesViewModel.seriesInfo.collectAsState()
    val volumes by seriesViewModel.volumes.collectAsState()
    val seriesTabsState by seriesViewModel.seriesTabsState.collectAsState()
    val totalVolumes = volumes.size
    val readVolumes = volumes.count { it.times_read > 0 }
    var showOwnedBottomSheet by remember { mutableStateOf(false) }
    var showReadBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var bottomSheetVolume by remember { mutableStateOf<Volume?>(null) }

    if (showOwnedBottomSheet || showReadBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showOwnedBottomSheet = false
                showReadBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.titleSmall,
                text = "Mark as:"
            )
            if (showOwnedBottomSheet) {
                ListItem(headlineContent = {
                    Text("Not owned", style = MaterialTheme.typography.bodyMedium)
                }, modifier = Modifier.clickable {
                    bottomSheetVolume?.let { volume ->
                        if (volume.user_volume_id != null) {
                            if (volume.times_read > 0) {
                                seriesViewModel.onUserVolumeUpdate(
                                    VolumeToUpdate(
                                        volume.user_volume_id,
                                        volume.id,
                                        volume.times_read,
                                        false
                                    )
                                )
                            } else if (volume.times_read == 0) {
                                seriesViewModel.onUserVolumeDelete(volume.user_volume_id, volume.id)
                            }

                        }
                        showOwnedBottomSheet = false
                    }
                })
            } else if (showReadBottomSheet) {
                ListItem(headlineContent = {
                    Text("Not read", style = MaterialTheme.typography.bodyMedium)
                }, modifier = Modifier.clickable {
                    bottomSheetVolume?.let { volume ->
                        if (volume.user_volume_id != null) {
                            if (volume.owned) {
                                seriesViewModel.onUserVolumeUpdate(
                                    VolumeToUpdate(
                                        volume.user_volume_id,
                                        volume.id,
                                        0,
                                        volume.owned
                                    )
                                )
                            } else if (!volume.owned) {
                                seriesViewModel.onUserVolumeDelete(volume.user_volume_id, volume.id)
                            }

                        }
                        showReadBottomSheet = false
                    }
                })
                HorizontalDivider()
                ListItem(headlineContent = {
                    Text("Reread", style = MaterialTheme.typography.bodyMedium)
                }, modifier = Modifier.clickable {
                    bottomSheetVolume?.let { volume ->
                        if (volume.user_volume_id != null) {
                            seriesViewModel.onUserVolumeUpdate(
                                VolumeToUpdate(
                                    volume.user_volume_id,
                                    volume.id,
                                    volume.times_read + 1,
                                    volume.owned
                                )
                            )
                        }
                        showReadBottomSheet = false
                    }
                })
            }
        }
    }

    Surface {
        if (series == null || volumes == null || seriesInfo == null) {
            Text("Loading series details...")
        } else {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Top,
            ) {
                series?.let { SeriesHeader(it, seriesInfo) }

                val readingProgress =
                    if (totalVolumes > 0) readVolumes.toFloat() / totalVolumes.toFloat() else 0f
                val ownedProgress = if (totalVolumes > 0) volumes.count { it.owned }
                    .toFloat() / totalVolumes.toFloat() else 0f
                SeriesProgressIndicator(ownedProgress, readingProgress, 4.dp)

                TabRow(
                    state = seriesTabsState,
                    titles = listOf(
                        stringResource(R.string.volumes_tab),
                        stringResource(R.string.about_tab)
                    ),
                    onTabClick = { newIndex ->
                        seriesViewModel.switchTab(newIndex)
                    })

                if (seriesTabsState == 0) {
                    LazyColumn {
                        items(volumes) { volume ->
                            VolumeListItem(
                                volume,
                                onItemClick = { selectedVolume ->
                                    seriesViewModel.selectVolume(volumes.indexOf(selectedVolume))
                                    toVolumeScreen()
                                },
                                onUserVolumeInsert = { volumeToInsert ->
                                    seriesViewModel.onUserVolumeInsert(volumeToInsert)
                                },
                                onUserVolumeUpdate = { volumeToUpdate ->
                                    seriesViewModel.onUserVolumeUpdate(volumeToUpdate)
                                },
                                onOwnedVolumeClick = {
                                    showOwnedBottomSheet = true
                                    bottomSheetVolume = volume
                                },
                                onReadClick = {
                                    showReadBottomSheet = true
                                    bottomSheetVolume = volume
                                },
                                isSingleVolume = series!!.is_single_volume
                            )
                            HorizontalDivider()
                        }
                    }
                } else {
                    series?.let { AboutSeries(it, seriesInfo) }
                }
            }
        }
    }
}