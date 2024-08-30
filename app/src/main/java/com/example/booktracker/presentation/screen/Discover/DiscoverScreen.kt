package com.example.booktracker.presentation.screen.Discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.data.model.Series
import com.example.booktracker.presentation.component.SeriesDialog
import com.example.booktracker.presentation.screen.Discover.component.SeriesCard
import com.example.booktracker.presentation.screen.Discover.component.SeriesSearchBar
import com.example.booktracker.ui.viewmodel.SeriesViewModel

@Composable
fun DiscoverScreen(seriesViewModel: SeriesViewModel = hiltViewModel()) {

    val seriesList by seriesViewModel.series.collectAsState()
    val volumeList by seriesViewModel.volumes.collectAsState()

    var selectedSeries by remember { mutableStateOf<Series?>(null) }

    val listState = rememberLazyGridState()

    val query by seriesViewModel.query.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column {

        SeriesSearchBar(
            query = query,
            onQueryChange = {
                seriesViewModel.onQueryChange(it)
                seriesViewModel.debounceSearch()
            },
            onSearch = {
                seriesViewModel.searchSeries()
                keyboardController?.hide()
                focusManager.clearFocus()
            })

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.Top,
            state = listState,
        ) {
            items(seriesList) { series ->
                SeriesCard(series = series, onCardClick = {
                    selectedSeries = series
                    seriesViewModel.fetchVolumes(series.id)
                })
            }
        }
    }



    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }.collect { visibleItems ->
            val lastVisibleItem = visibleItems.lastOrNull()
            val lastVisibleItemIndex = lastVisibleItem?.index ?: 0
            val totalItemsCount = listState.layoutInfo.totalItemsCount

            if (lastVisibleItemIndex >= totalItemsCount - 6 && totalItemsCount > 0) {
                seriesViewModel.fetchSeries()
            }
        }
    }

    selectedSeries?.let {
        SeriesDialog(series = it, volumeList = volumeList, onDismiss = { selectedSeries = null })
    }
}