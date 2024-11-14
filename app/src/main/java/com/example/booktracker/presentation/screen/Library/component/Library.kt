package com.example.booktracker.presentation.screen.Library.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.booktracker.presentation.screen.Library.LibraryViewModel
import com.example.booktracker.ui.viewmodel.SeriesViewModel

@Composable
fun Library(
    seriesViewModel: SeriesViewModel,
    libraryViewModel: LibraryViewModel,
    toSeriesScreen: () -> Unit
) {

    val userSeries by libraryViewModel.userSeries.collectAsState()
    val refreshFlag by seriesViewModel.seriesRefreshFlag.collectAsState()

    LaunchedEffect(refreshFlag) {
        if (refreshFlag) {
            libraryViewModel.refreshSeries()
            seriesViewModel.resetRefreshFlag()
        }
    }
    LazyVerticalGrid(columns = GridCells.Fixed(3), verticalArrangement = Arrangement.Top) {
        items(userSeries) { followedSeries ->
            SeriesCard(
                series = followedSeries.series,
                readVolumes = followedSeries.volumes_read_count,
                ownedVolumes = followedSeries.volumes_owned_count,
                onCardClick = {
                    toSeriesScreen()
                    seriesViewModel.loadSeriesDetails(followedSeries.series, followedSeries.series.id)
                })
        }
    }
}