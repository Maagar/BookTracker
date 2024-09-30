package com.example.booktracker.presentation.dialog.VolumeDialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToInsert
import com.example.booktracker.data.model.VolumeToUpdate
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
        val nextVolumeIndex = volumeIndex + 1
        val previousVolumeIndex = volumeIndex - 1

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
                        Text(text = "${volume.release_date ?: "Release date not yet announced"}")
                    }
                }

                if (volumeList.size > 1)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .height(IntrinsicSize.Min)
                    ) {
                        Column(
                            modifier
                                .fillMaxWidth()
                                .weight(0.5f)
                                .padding(8.dp)
                                .clickable {
                                    if (previousVolumeIndex >= 0) onVolumeChange(
                                        previousVolumeIndex
                                    )
                                },
                            horizontalAlignment = Alignment.End
                        ) {
                            if (volumeIndex > 0) {
                                Text("Previous volume")
                                AsyncImage(
                                    volumeList[previousVolumeIndex].cover_url,
                                    null,
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                        }

                        VerticalDivider()

                        Column(
                            modifier
                                .fillMaxWidth()
                                .weight(0.5f)
                                .padding(8.dp)
                                .clickable {
                                    if (nextVolumeIndex <= volumeList.lastIndex) onVolumeChange(
                                        nextVolumeIndex
                                    )
                                },
                            horizontalAlignment = Alignment.Start
                        ) {
                            if (volumeIndex < volumeList.lastIndex) {
                                Text("Next volume")
                                AsyncImage(
                                    volumeList[nextVolumeIndex].cover_url,
                                    null,
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                        }

                    }
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