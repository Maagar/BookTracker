package com.example.booktracker.presentation.dialog.SeriesDialog.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.SeriesInfo
import com.example.ui.theme.AppTypography

@Composable
fun SeriesHeader(series: Series, seriesInfo: SeriesInfo) {
    Row(
        modifier = Modifier
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
}