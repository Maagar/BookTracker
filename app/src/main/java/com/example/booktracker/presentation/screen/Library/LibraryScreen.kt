package com.example.booktracker.presentation.screen.Library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.R
import com.example.booktracker.presentation.screen.Library.component.Library
import com.example.booktracker.presentation.screen.Library.component.Upcoming
import com.example.booktracker.presentation.component.TabRow
import com.example.booktracker.ui.viewmodel.SeriesViewModel

@Composable
fun LibraryScreen(
    seriesViewModel: SeriesViewModel,
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    toSeriesScreen: (() -> Unit),
    toVolumeScreen: (() -> Unit)
) {
    val libraryTabsState by libraryViewModel.libraryTabsState.collectAsState()


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
}