package com.example.booktracker.presentation.screen.Series

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.booktracker.presentation.screen.Series.component.AboutSeries
import com.example.booktracker.presentation.screen.Series.component.DialogTabs
import com.example.booktracker.presentation.screen.Series.component.SeriesHeader
import com.example.booktracker.presentation.screen.Series.component.VolumeListItem
import com.example.booktracker.presentation.component.SeriesProgressIndicator
import com.example.booktracker.presentation.dialog.VolumeDialog.VolumeDialog
import com.example.booktracker.ui.viewmodel.SeriesViewModel

@Composable
fun SeriesScreen(
    seriesViewModel: SeriesViewModel
) {
    val series by seriesViewModel.series.collectAsState()
    val seriesInfo by seriesViewModel.seriesInfo.collectAsState()
    val volumes by seriesViewModel.volumes.collectAsState()
    val dialogState by seriesViewModel.dialogState.collectAsState()
    val totalVolumes = volumes.size
    val readVolumes = volumes.count { it.times_read > 0 }

    var volumeDialogState by remember { mutableStateOf(false) }
    var selectedVolume by remember { mutableStateOf<Int?>(null) }

    Surface(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .fillMaxHeight(0.95f)
            .clip(RoundedCornerShape(16.dp))
    ) {
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
            DialogTabs(
                state = dialogState,
                titles = listOf("VOLUMES", "ABOUT"),
                onTabClick = { newIndex ->
                    seriesViewModel.switchTab(newIndex) })
            if (dialogState == 0) {
                LazyColumn {
                    items(volumes) { volume ->
                        VolumeListItem(volume,
                            onItemClick = { volume ->
                                selectedVolume = volumes.indexOf(volume)
                            },
                            onUserVolumeInsert = { volumeToInsert ->
                                seriesViewModel.onUserVolumeInsert(volumeToInsert)
                            },
                            onUserVolumeUpdate = { volumeToUpdate ->
                                seriesViewModel.onUserVolumeUpdate(volumeToUpdate)
                            }
                        )
                        HorizontalDivider()
                    }
                }
            } else {
                series?.let { AboutSeries(it, seriesInfo) }
            }
        }
    }

    selectedVolume?.let { volumeIndex ->
        volumeDialogState = true
        VolumeDialog(
            volumeIndex = volumeIndex,
            volumeList = volumes,
            onDismiss = { selectedVolume = null },
            dialogState = volumeDialogState,
            onUserVolumeInsert = { volumeToInsert ->
                seriesViewModel.onUserVolumeInsert(volumeToInsert)
            },
            onUserVolumeUpdate = { volumeToUpdate ->
                seriesViewModel.onUserVolumeUpdate(volumeToUpdate)
            },
            onVolumeChange = { newIndex ->
                selectedVolume = newIndex
            }
        )
    }
}