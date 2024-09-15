package com.example.booktracker.presentation.screen.Library

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.data.model.Series
import com.example.booktracker.presentation.SeriesDialog.SeriesDialog
import com.example.booktracker.presentation.screen.Library.component.SeriesCard
import com.example.booktracker.ui.viewmodel.SeriesViewModel

@Composable
fun LibraryScreen(
    seriesViewModel: SeriesViewModel,
    libraryViewModel: LibraryViewModel = hiltViewModel()
) {

    val userSeries by libraryViewModel.userSeries.collectAsState()
    val volumeList by seriesViewModel.volumes.collectAsState()
    val refreshFlag by seriesViewModel.seriesRefreshFlag.collectAsState()
    var selectedSeries by remember { mutableStateOf<Series?>(null) }
    val seriesInfo by seriesViewModel.seriesInfo.collectAsState()
    val dialogState by seriesViewModel.dialogState.collectAsState()


    LaunchedEffect(refreshFlag) {
        if (refreshFlag) {
            Log.d("LibraryScreen", "Refresh Flag: $refreshFlag")
            libraryViewModel.refreshSeries()
            seriesViewModel.resetRefreshFlag()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(3), verticalArrangement = Arrangement.Top) {
            items(userSeries) { series ->
                SeriesCard(
                    series = series.series,
                    readVolumes = series.volumes_read_count,
                    onCardClick = {
                        selectedSeries = series.series
                        seriesViewModel.fetchVolumes(series.id)
                        seriesViewModel.fetchSeriesInfo(series.id)
                    })
            }
        }

    }

    selectedSeries?.let {
        SeriesDialog(
            series = it,
            seriesInfo = seriesInfo,
            readVolumes = volumeList.count { it.userVolumes.times_read > 0 },
            volumeList = volumeList,
            onDismiss = {
                selectedSeries = null
                seriesViewModel.clearVolumeList()
            },
            dialogState = dialogState,
            onTabClick = { newIndex ->
                seriesViewModel.switchTab(newIndex)
            })
    }
}