package com.example.booktracker.presentation.screen.Discover.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.UserSeries

@Composable
fun SeriesCard(series: UserSeries, onCardClick: (() -> Unit) = {}) {
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
        }
    }
}