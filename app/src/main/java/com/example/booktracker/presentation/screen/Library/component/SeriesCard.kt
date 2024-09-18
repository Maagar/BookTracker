package com.example.booktracker.presentation.screen.Library.component

import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
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
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = onCardClick
    ) {
        Column {
            AsyncImage(model = series.main_cover_url, contentDescription = null)

            val readingProgress =
                if (series.total_volumes_released > 0) readVolumes.toFloat() / series.total_volumes_released else 0f
            val ownedProgress =
                if (series.total_volumes_released > 0) ownedVolumes.toFloat() / series.total_volumes_released else 0f

            SeriesProgressIndicator(ownedProgress, readingProgress, 4.dp)
        }

    }
}