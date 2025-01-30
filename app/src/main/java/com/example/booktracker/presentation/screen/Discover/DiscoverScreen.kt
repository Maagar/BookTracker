package com.example.booktracker.presentation.screen.Discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.presentation.screen.Discover.component.SeriesListItem
import com.example.booktracker.presentation.screen.Discover.component.SeriesSearchBar
import com.example.booktracker.presentation.ui.viewmodel.SeriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    seriesViewModel: SeriesViewModel,
    discoverViewModel: DiscoverViewModel = hiltViewModel(),
    toSeriesScreen: (() -> Unit)
) {

    val seriesList by discoverViewModel.series.collectAsState()
    val listState = rememberLazyListState()

    val query by discoverViewModel.query.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val refreshState = rememberPullToRefreshState()
    val isRefreshing by discoverViewModel.isRefreshing.collectAsState()


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
        PullToRefreshBox(state = refreshState, onRefresh = {
            discoverViewModel.setIsRefreshing()
            discoverViewModel.fetchSeries()
        }, isRefreshing = isRefreshing)
        {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(seriesList) { series ->
                    SeriesListItem(series = series, onItemClick = {
                        seriesViewModel.selectSeries(series)
                        seriesViewModel.fetchVolumes(series.id)
                        seriesViewModel.fetchSeriesInfo(series.id)
                        toSeriesScreen()
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
                    HorizontalDivider(modifier = Modifier.padding(8.dp))
                }
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
    }
}