package com.example.booktracker.presentation.screen.Library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import com.example.booktracker.presentation.screen.Library.component.FilterBottomSheet
import com.example.booktracker.presentation.ui.viewmodel.SeriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    seriesViewModel: SeriesViewModel,
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    toSeriesScreen: (() -> Unit),
    toVolumeScreen: (() -> Unit)
) {
    val libraryTabsState by libraryViewModel.libraryTabsState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sortByDate by libraryViewModel.sortByDate.collectAsState()
    val showFinished by libraryViewModel.showFinished.collectAsState()

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
                })
            if (libraryTabsState == 0) {
                Library(seriesViewModel, libraryViewModel, toSeriesScreen)
            } else if (libraryTabsState == 1) {
                Upcoming(seriesViewModel, libraryViewModel, toSeriesScreen, toVolumeScreen)
            }

        }

        if(libraryTabsState == 0) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = { showBottomSheet = true }) {
                Icon(painterResource(R.drawable.filter_list), contentDescription = null)
            }
        }


        if (showBottomSheet) {
            FilterBottomSheet(
                sheetState,
                { showBottomSheet = false },
                sortByDate,
                showFinished,
                onSortByChange = {
                    libraryViewModel.updateSorting(!sortByDate)
                    libraryViewModel.refreshSeries()
                },
                onShowFinishedChange = {
                    libraryViewModel.updateShowFinished(!showFinished)
                    libraryViewModel.refreshSeries()
                })
        }
    }
}