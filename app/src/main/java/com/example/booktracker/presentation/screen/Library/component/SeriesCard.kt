package com.example.booktracker.presentation.screen.Library.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booktracker.data.model.Series
import com.example.booktracker.presentation.component.SeriesProgressIndicator

@Composable
fun SeriesCard(
    series: Series,
    readVolumes: Int,
    ownedVolumes: Int,
    onCardClick: (() -> Unit) = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = onCardClick
    ) {
        Column {
            var isLoading by remember { mutableStateOf(true) }
            AsyncImage(
                model = series.main_cover_url,
                contentDescription = null,
            )

            val readingProgress =
                if (series.total_volumes_released > 0) readVolumes.toFloat() / series.total_volumes_released else 0f
            val ownedProgress =
                if (series.total_volumes_released > 0) ownedVolumes.toFloat() / series.total_volumes_released else 0f

            SeriesProgressIndicator(ownedProgress, readingProgress, 4.dp)
        }

    }
}