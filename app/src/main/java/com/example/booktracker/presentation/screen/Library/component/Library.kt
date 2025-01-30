package com.example.booktracker.presentation.screen.Library.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.example.booktracker.data.model.FollowedSeries
import com.example.booktracker.presentation.screen.Library.LibraryViewModel
import com.example.booktracker.presentation.ui.viewmodel.SeriesViewModel

@Composable
fun Library(
    seriesViewModel: SeriesViewModel,
    libraryViewModel: LibraryViewModel,
    userSeries: List<FollowedSeries>,
    toSeriesScreen: () -> Unit
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.Top,
        state = gridState,
        modifier = Modifier.fillMaxSize()
    ) {
        items(userSeries) { followedSeries ->
            SeriesCard(
                series = followedSeries.series,
                readVolumes = followedSeries.volumes_read_count,
                ownedVolumes = followedSeries.volumes_owned_count,
                onCardClick = {
                    toSeriesScreen()
                    seriesViewModel.loadSeriesDetails(
                        followedSeries.series,
                        followedSeries.series.id
                    )
                })
        }
    }

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo }.collect { visibleItems ->
            val lastVisibleItem = visibleItems.lastOrNull()
            val lastVisibleItemIndex = lastVisibleItem?.index ?: 0
            val totalItemsCount = gridState.layoutInfo.totalItemsCount

            if (lastVisibleItemIndex >= totalItemsCount - 6 && totalItemsCount > 0) {
                libraryViewModel.fetchFollowedSeries()
            }
        }
    }
}