package com.example.booktracker.presentation.screen.Discover.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.booktracker.data.model.Series

@Composable
fun Recommendations(
    recommendations: List<Series>,
    recommendationState: Boolean,
    onIconClick: () -> Unit,
    onSeriesClick: (Series) -> Unit,
    onFollowSeries: (Series) -> Unit,
    onUnfollowSeries: (Series) -> Unit,
    onTextClick: () -> Unit
) {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Recommendations", modifier = Modifier.padding(start = 24.dp))
            IconButton(onClick = onIconClick, modifier = Modifier.padding(end = 24.dp)) {
                Icon(
                    imageVector = if (recommendationState) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = null
                )
            }
        }
        if (recommendationState) {
            if (recommendations.isEmpty()) {
                CircularProgressIndicator()
                Text("Curating stories you'll love...")
            } else {
                recommendations.forEach { series ->
                    SeriesListItem(
                        onItemClick = { onSeriesClick(series) },
                        series = series,
                        onFollowSeries = { onFollowSeries(series) },
                        onUnfollowSeries = { onUnfollowSeries(series) }
                    )
                }
                TextButton(onClick = onTextClick) {
                    Text("Refresh recommendations")
                }
            }
        }
        HorizontalDivider()
    }
}