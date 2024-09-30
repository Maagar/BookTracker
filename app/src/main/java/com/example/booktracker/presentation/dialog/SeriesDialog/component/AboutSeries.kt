package com.example.booktracker.presentation.dialog.SeriesDialog.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.SeriesInfo
import com.example.ui.theme.AppTypography

@Composable
fun AboutSeries(series: Series, seriesInfo: SeriesInfo) {

    var maxTagLines by remember { mutableStateOf(1) }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Card(modifier = Modifier.padding(8.dp), onClick = {
            if (maxTagLines == 1) maxTagLines = Int.MAX_VALUE
            else maxTagLines = 1
        }) {
            Text(
                "Tags: ${seriesInfo.tagList.joinToString(", ")}",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                style = AppTypography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }
        Card(
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Synopsis", style = AppTypography.titleMedium, modifier = Modifier.padding(8.dp))
            Text(
                series.synopsis,
                style = AppTypography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}