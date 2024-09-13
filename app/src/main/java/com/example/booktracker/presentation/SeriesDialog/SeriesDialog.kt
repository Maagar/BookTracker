package com.example.booktracker.presentation.SeriesDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.SeriesInfo
import com.example.booktracker.data.model.Volume
import com.example.booktracker.presentation.SeriesDialog.component.AboutSeries
import com.example.booktracker.presentation.SeriesDialog.component.DialogTabs
import com.example.booktracker.presentation.SeriesDialog.component.VolumeListItem
import com.example.ui.theme.AppTypography

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
    onTabClick: (Int) -> Unit
) {
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
                Row(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = series.main_cover_url,
                        contentDescription = null,
                        modifier = Modifier
                            .weight(0.4f)
                            .wrapContentSize()
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp).weight(0.6f),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(text = series.title, style = AppTypography.titleLarge)
                        Text(text = "Volumes: ${series.total_volumes_released}")
                        Text(text = "Author: ${seriesInfo.authorList.joinToString(", ")}")
                        Text(text = "Publisher: ${seriesInfo.publisherList.joinToString(", ")}")

                    }
                }

                val progress = if (totalVolumes > 0) {
                    readVolumes.toFloat() / totalVolumes.toFloat()
                } else 0f
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { progress }
                )
                DialogTabs(
                    state = dialogState,
                    titles = listOf("VOLUMES", "ABOUT"),
                    onTabClick = { newIndex -> onTabClick(newIndex) })
                if (dialogState == 0) {
                    LazyColumn {
                        items(volumeList) { volume ->
                            VolumeListItem(volume)
                            HorizontalDivider()
                        }
                    }
                } else {
                    AboutSeries(series, seriesInfo)
                }


            }
        }
    }
}