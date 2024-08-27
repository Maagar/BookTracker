package com.example.booktracker.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.Volume
import com.example.ui.theme.AppTypography

@Composable
fun SeriesDialog(
    series: Series,
    volumeList: List<Volume>,
    onDismiss: (() -> Unit),
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.9f)
        ) {
            Column(
                modifier = modifier.padding(8.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Row(
                    modifier = modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AsyncImage(model = series.main_cover_url, contentDescription = null)

                    Text(text = series.title, style = AppTypography.titleMedium)
                }
                LazyColumn {
                    items(volumeList) { volume ->
                        Row {
                            AsyncImage(model = volume.cover_url, contentDescription = null)
                            Column {
                                Text(text = volume.title)
                                Text(text = "Times read: ${volume.timesRead}")
                            }
                            
                        }
                    }
                }

            }
        }
    }
}