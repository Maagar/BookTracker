package com.example.booktracker.utils

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.booktracker.presentation.ui.viewmodel.SeriesViewModel


val LocalSeriesViewModel = staticCompositionLocalOf<SeriesViewModel> {
    error("SeriesViewModel not provided")
}