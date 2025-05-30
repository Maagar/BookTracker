package com.example.booktracker.presentation.screen.Series.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booktracker.R
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
                .aspectRatio(0.7f)
                .fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(0.6f),
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = series.title, style = AppTypography.titleLarge)
            Text(text = stringResource(R.string.volumes, series.total_volumes_released))
            Text(text = stringResource(R.string.author, seriesInfo.authorList.joinToString(", ")))
            Text(text = stringResource(
                R.string.publisher,
                seriesInfo.publisherList.joinToString(", ")
            ))

        }
    }
}