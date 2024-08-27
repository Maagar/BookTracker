package com.example.booktracker.presentation.screen.Discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.data.model.Series
import com.example.booktracker.presentation.component.SeriesDialog
import com.example.booktracker.presentation.screen.Discover.component.SeriesCard
import com.example.booktracker.ui.viewmodel.SeriesViewModel

@Composable
fun DiscoverScreen(seriesViewModel: SeriesViewModel = hiltViewModel()) {

    val seriesList by seriesViewModel.series.collectAsState()
    val volumeList by seriesViewModel.volumes.collectAsState()

    var selectedSeries by remember { mutableStateOf<Series?>(null) }

    LazyVerticalGrid(columns = GridCells.Fixed(3), verticalArrangement = Arrangement.Top) {
        items(seriesList) { series ->
            SeriesCard(series = series, onCardClick = {
                selectedSeries = series
                seriesViewModel.fetchVolumes(series.id)
            })
        }

    }

    selectedSeries?.let {
        SeriesDialog(series = it, volumeList = volumeList, onDismiss = { selectedSeries = null })
    }
}