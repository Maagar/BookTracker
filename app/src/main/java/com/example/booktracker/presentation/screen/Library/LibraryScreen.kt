package com.example.booktracker.presentation.screen.Library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.presentation.screen.Discover.component.SeriesCard
import com.example.booktracker.ui.viewmodel.SeriesViewModel

@Composable
fun LibraryScreen(
    seriesViewModel: SeriesViewModel = hiltViewModel(),
    libraryViewModel: LibraryViewModel = hiltViewModel()
) {

    val userSeries by libraryViewModel.userSeries.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(3), verticalArrangement = Arrangement.Top) {
            items(userSeries) { series ->
                SeriesCard(series = series.series)
            }
        }

    }
}