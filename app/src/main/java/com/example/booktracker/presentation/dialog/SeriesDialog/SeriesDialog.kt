package com.example.booktracker.presentation.dialog.SeriesDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.SeriesInfo
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToInsert
import com.example.booktracker.data.model.VolumeToUpdate
import com.example.booktracker.presentation.dialog.SeriesDialog.component.AboutSeries
import com.example.booktracker.presentation.dialog.SeriesDialog.component.DialogTabs
import com.example.booktracker.presentation.dialog.SeriesDialog.component.SeriesHeader
import com.example.booktracker.presentation.dialog.SeriesDialog.component.VolumeListItem
import com.example.booktracker.presentation.component.SeriesProgressIndicator
import com.example.booktracker.presentation.dialog.VolumeDialog.VolumeDialog

@Composable
fun SeriesDialog(
    modifier: Modifier = Modifier,
    series: Series,
    seriesInfo: SeriesInfo,
    totalVolumes: Int = series.total_volumes_released,
    readVolumes: Int,
    volumeList: List<Volume>,
    onDismiss: (() -> Unit),
    dialogState: Int,
    onTabClick: (Int) -> Unit,
    onVolumeInsert: (VolumeToInsert) -> Unit,
    onVolumeUpdate: (VolumeToUpdate) -> Unit
) {

    var volumeDialogState by remember { mutableStateOf(false) }
    var selectedVolume by remember { mutableStateOf<Int?>(null) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.95f)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = modifier.padding(8.dp),
                verticalArrangement = Arrangement.Top,
            ) {
                SeriesHeader(series, seriesInfo)

                val readingProgress =
                    if (totalVolumes > 0) readVolumes.toFloat() / totalVolumes.toFloat() else 0f
                val ownedProgress = if (totalVolumes > 0) volumeList.count { it.owned }
                    .toFloat() / totalVolumes.toFloat() else 0f
                SeriesProgressIndicator(ownedProgress, readingProgress, 4.dp)
                DialogTabs(
                    state = dialogState,
                    titles = listOf("VOLUMES", "ABOUT"),
                    onTabClick = { newIndex -> onTabClick(newIndex) })
                if (dialogState == 0) {
                    LazyColumn {
                        items(volumeList) { volume ->
                            VolumeListItem(volume,
                                onItemClick = { volume ->
                                    selectedVolume = volumeList.indexOf(volume)
                                },
                                onUserVolumeInsert = { volumeToInsert ->
                                    onVolumeInsert(volumeToInsert)
                                },
                                onUserVolumeUpdate = { volumeToUpdate ->
                                    onVolumeUpdate(volumeToUpdate)
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                } else {
                    AboutSeries(series, seriesInfo)
                }


            }
        }
    }

    selectedVolume?.let { volumeIndex ->
        volumeDialogState = true
        VolumeDialog(
            volumeIndex = volumeIndex,
            volumeList = volumeList,
            onDismiss = { selectedVolume = null },
            dialogState = volumeDialogState,
            onUserVolumeInsert = { volumeToInsert ->
                onVolumeInsert(volumeToInsert)
            },
            onUserVolumeUpdate = { volumeToUpdate ->
                onVolumeUpdate(volumeToUpdate)
            },
            onVolumeChange = { newIndex ->
                selectedVolume = newIndex
            }
        )
    }
}