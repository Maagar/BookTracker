package com.example.booktracker.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(private val seriesRepository: SeriesRepository): ViewModel() {

    private val _series = MutableStateFlow<List<Series>>(emptyList())
    val series: StateFlow<List<Series>> = _series

    init {
        fetchSeries()
    }

    private fun fetchSeries() {
        viewModelScope.launch {
            try {
                val seriesList = seriesRepository.getSeries()
                _series.value = seriesList
            } catch (e: Exception) {
                Log.e("SeriesViewModel", "Error fetching series", e)
            }
        }
    }
}