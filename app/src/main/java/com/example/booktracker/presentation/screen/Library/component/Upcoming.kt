package com.example.booktracker.presentation.screen.Library.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.booktracker.presentation.screen.Library.LibraryViewModel
import com.example.booktracker.ui.viewmodel.SeriesViewModel

@Composable
fun Upcoming(
    seriesViewModel: SeriesViewModel,
    libraryViewModel: LibraryViewModel,
    toSeriesScreen: () -> Unit,
    toVolumeScreen: () -> Unit
) {

    val listState = rememberLazyListState()
    val upcomingVolumes by libraryViewModel.upcomingVolumes.collectAsState(emptyList())

    LazyColumn(verticalArrangement = Arrangement.Top, state = listState) {
        items(upcomingVolumes) { upcomingVolume ->
            UpcomingListItem(
                upcomingVolume = upcomingVolume,
                toSeriesScreen = {
                    seriesViewModel.loadSeriesDetails(seriesId = upcomingVolume.series_id)
                    toSeriesScreen()
                },
                toVolumeScreen = {
                    seriesViewModel.onVolumeSelected(
                        seriesId = upcomingVolume.series_id,
                        volumeId = upcomingVolume.id
                    ) {
                        toVolumeScreen()
                    }
                })
            HorizontalDivider(modifier = Modifier.padding(4.dp))
        }
    }
}