package com.example.booktracker.presentation.screen.Library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.presentation.screen.Library.component.SeriesCard
import com.example.booktracker.ui.viewmodel.SeriesViewModel

@Composable
fun LibraryScreen(
    seriesViewModel: SeriesViewModel,
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    toSeriesScreen: (() -> Unit)
) {

    val userSeries by libraryViewModel.userSeries.collectAsState()
    val refreshFlag by seriesViewModel.seriesRefreshFlag.collectAsState()


    LaunchedEffect(refreshFlag) {
        if (refreshFlag) {
            libraryViewModel.refreshSeries()
            seriesViewModel.resetRefreshFlag()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(3), verticalArrangement = Arrangement.Top) {
            items(userSeries) { followedSeries ->
                SeriesCard(
                    series = followedSeries.series,
                    readVolumes = followedSeries.volumes_read_count,
                    ownedVolumes = followedSeries.volumes_owned_count,
                    onCardClick = {
                        seriesViewModel.clearSelectedSeries()
                        seriesViewModel.clearVolumeList()
                        seriesViewModel.selectSeries(followedSeries.series)
                        seriesViewModel.fetchVolumes(followedSeries.series.id)
                        seriesViewModel.fetchSeriesInfo(followedSeries.series.id)
                        toSeriesScreen()
                    })
            }
        }

    }
}