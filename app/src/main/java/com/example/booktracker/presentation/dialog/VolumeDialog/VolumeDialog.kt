package com.example.booktracker.presentation.dialog.VolumeDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.booktracker.R
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToInsert
import com.example.booktracker.data.model.VolumeToUpdate
import com.example.booktracker.presentation.dialog.VolumeDialog.component.VolumeChangeRow
import com.example.ui.theme.AppTypography

@Composable
fun VolumeDialog(
    modifier: Modifier = Modifier,
    volumeIndex: Int,
    volumeList: List<Volume>,
    onDismiss: (() -> Unit),
    dialogState: Boolean,
    onUserVolumeInsert: (VolumeToInsert) -> Unit,
    onUserVolumeUpdate: (VolumeToUpdate) -> Unit,
    onVolumeChange: (Int) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        val volume = volumeList[volumeIndex]
        val isLast = volumeList.lastIndex == volumeIndex
        val isFirst = volumeIndex == 0

        Surface(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.95f)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = modifier
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = volume.cover_url,
                        null,
                        modifier
                            .weight(0.4f)
                            .wrapContentSize()
                    )
                    Column(
                        modifier = Modifier
                            .weight(0.6f)
                            .padding(start = 8.dp)
                    ) {
                        Text(text = volume.title, style = AppTypography.titleLarge)
                        Row {
                            Icon(painterResource(R.drawable.calendar_month), contentDescription = null, modifier = Modifier.padding(end = 12.dp))
                            Text(text = "${volume.release_date ?: "Release date not yet announced"}")
                        }
                        Row {
                            Icon(painterResource(R.drawable.book), contentDescription = null, modifier = Modifier.padding(end = 12.dp))
                            Text(text = "${volume.read_date ?: "Not read yet"}")
                        }
                    }
                }

                if (volumeList.size > 1)
                    VolumeChangeRow(
                        onClickNext = { if (!isLast) onVolumeChange(volumeIndex + 1) },
                        onClickBack = { if (!isFirst) onVolumeChange(volumeIndex - 1) },
                        nextVolume = if (!isLast) volumeList[volumeIndex + 1] else null,
                        previousVolume = if (!isFirst) volumeList[volumeIndex - 1] else null
                    )

                Column {
                    Card(modifier = Modifier.padding(8.dp)) {
                        Text(
                            "Synopsis:\n ${volume.synopsis}",
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            style = AppTypography.bodyMedium,
                        )
                    }
                }
            }

        }
    }
}