package com.example.booktracker.presentation.screen.Discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.booktracker.ui.viewmodel.SeriesViewModel

@Composable
fun DiscoverScreen(seriesViewModel: SeriesViewModel = hiltViewModel()) {
    
    val seriesList by seriesViewModel.series.collectAsState()
    
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Discover screen")
        LazyColumn {
            items(seriesList) { series ->
                Row {
                    Text(text = "Title: ${series.title}")
                    Text(text = "URL: ${series.main_cover_url}")
                }
            }
        }
    }
}