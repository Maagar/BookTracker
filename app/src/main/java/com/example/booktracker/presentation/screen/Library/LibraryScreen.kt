package com.example.booktracker.presentation.screen.Library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.R
import com.example.booktracker.presentation.screen.Library.component.Library
import com.example.booktracker.presentation.screen.Library.component.Upcoming
import com.example.booktracker.presentation.component.TabRow
import com.example.booktracker.presentation.screen.Library.component.EmptyLibraryMessage
import com.example.booktracker.presentation.screen.Library.component.FilterBottomSheet
import com.example.booktracker.presentation.ui.viewmodel.SeriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    seriesViewModel: SeriesViewModel,
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    toSeriesScreen: () -> Unit,
    toVolumeScreen: () -> Unit
) {
    val libraryTabsState by libraryViewModel.libraryTabsState.collectAsState()
    val sortByDate by libraryViewModel.sortByDate.collectAsState()
    val showFinished by libraryViewModel.showFinished.collectAsState()
    val userSeries by libraryViewModel.userSeries.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val refreshFlag by seriesViewModel.seriesRefreshFlag.collectAsState()

    val followedRefreshState = rememberPullToRefreshState()
    val upcomingRefreshState = rememberPullToRefreshState()
    val isFollowedRefreshing by libraryViewModel.isFollowedRefreshing.collectAsState()
    val isUpcomingRefreshing by libraryViewModel.isUpcomingRefreshing.collectAsState()

    LaunchedEffect(refreshFlag) {
        if (refreshFlag) {
            libraryViewModel.refreshFollowedSeries()
            libraryViewModel.refreshUpcomingSeries()
            seriesViewModel.resetRefreshFlag()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TabRow(
                state = libraryTabsState,
                titles = listOf(
                    stringResource(R.string.library).uppercase(),
                    stringResource(R.string.upcoming)
                ),
                onTabClick = { newIndex ->
                    libraryViewModel.switchTab(newIndex)
                }
            )
            if (libraryTabsState == 0) {
                PullToRefreshBox(
                    state = followedRefreshState,
                    onRefresh = {
                        libraryViewModel.startFollowedRefreshing()
                        libraryViewModel.refreshFollowedSeries()
                    },
                    isRefreshing = isFollowedRefreshing
                ) {
                    if (userSeries.isEmpty()) {
                        EmptyLibraryMessage()
                    } else {
                        Library(seriesViewModel, libraryViewModel, userSeries, toSeriesScreen)
                    }
                }
            } else if (libraryTabsState == 1) {
                PullToRefreshBox(
                    state = upcomingRefreshState,
                    onRefresh = {
                        libraryViewModel.startUpcomingRefreshing()
                        libraryViewModel.refreshUpcomingSeries()
                    },
                    isRefreshing = isUpcomingRefreshing
                ) {
                    Upcoming(seriesViewModel, libraryViewModel, toSeriesScreen, toVolumeScreen)
                }
            }

            if (showBottomSheet) {
                FilterBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false },
                    sortByDate = sortByDate,
                    showFinished = showFinished,
                    onSortByChange = {
                        libraryViewModel.updateSorting(!sortByDate)
                        libraryViewModel.refreshFollowedSeries()
                    },
                    onShowFinishedChange = {
                        libraryViewModel.updateShowFinished(!showFinished)
                        libraryViewModel.refreshFollowedSeries()
                    }
                )
            }
        }
        if (libraryTabsState == 0 && userSeries.isNotEmpty()) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = { showBottomSheet = true }
            ) {
                Icon(painterResource(R.drawable.filter_list), contentDescription = null)
            }
        }
    }
}