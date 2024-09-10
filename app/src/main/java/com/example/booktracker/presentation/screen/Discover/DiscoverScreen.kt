package com.example.booktracker.presentation.screen.Discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
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
import com.example.booktracker.presentation.screen.Discover.component.SeriesListItem
import com.example.booktracker.presentation.screen.Discover.component.SeriesSearchBar
import com.example.booktracker.ui.viewmodel.SeriesViewModel

@Composable
fun DiscoverScreen(
    seriesViewModel: SeriesViewModel,
    discoverViewModel: DiscoverViewModel = hiltViewModel()
) {

    val seriesList by discoverViewModel.series.collectAsState()
    val volumeList by seriesViewModel.volumes.collectAsState()

    var selectedSeries by remember { mutableStateOf<Series?>(null) }

    val listState = rememberLazyListState()

    val query by discoverViewModel.query.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        discoverViewModel.fetchSeries()
    }

    Column {

        SeriesSearchBar(
            query = query,
            onQueryChange = {
                discoverViewModel.onQueryChange(it)
                discoverViewModel.debounceSearch()
            },
            onSearch = {
                discoverViewModel.searchSeries()
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            onClose = {
                discoverViewModel.resetQuery()
                discoverViewModel.searchSeries()
                keyboardController?.hide()
                focusManager.clearFocus()
            })

        LazyColumn(
            verticalArrangement = Arrangement.Top,
            state = listState
        ) {
            items(seriesList) { series ->
                SeriesListItem(series = series, onItemClick = {
                    selectedSeries = series
                    seriesViewModel.fetchVolumes(series.id)
                },
                    onFollowSeries = {
                        seriesViewModel.onFollowSeries(series.id) { success ->
                            if (success) {
                                discoverViewModel.refreshSeries(series.id)
                            }
                        }
                    },
                    onUnfollowSeries = {
                        seriesViewModel.onUnfollowSeries(series.id) { success ->
                            if (success) {
                                discoverViewModel.refreshSeries(series.id)
                            }
                        }
                    })
                HorizontalDivider()
            }
        }

        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo }.collect { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()
                val lastVisibleItemIndex = lastVisibleItem?.index ?: 0
                val totalItemsCount = listState.layoutInfo.totalItemsCount

                if (lastVisibleItemIndex >= totalItemsCount - 6 && totalItemsCount > 0) {
                    discoverViewModel.fetchSeries()
                }
            }
        }

        selectedSeries?.let {
            SeriesDialog(
                series = it,
                volumeList = volumeList,
                onDismiss = { selectedSeries = null })
        }
    }
}