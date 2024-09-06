package com.example.booktracker.presentation.screen.Discover

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(private val seriesRepository: SeriesRepository) : ViewModel() {

    private val _series = MutableStateFlow<List<Series>>(emptyList())
    val series: StateFlow<List<Series>> = _series

    private var currentPage = 0
    private val pageSize = 20
    private var isLoading = false
    private var isLastPage = false

    private var isSearching = false

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    init {
        fetchSeries()
    }

    fun onQueryChange(query: String) {
        _query.value = query
        debounceSearch()
    }

    fun resetQuery() {
        _query.value = ""
    }

    fun fetchSeries() {
        if (isLoading || isLastPage) return

        viewModelScope.launch {
            isLoading = true
            try {
                val newSeries = seriesRepository.getSeries(
                    page = currentPage,
                    pageSize = pageSize,
                    searchQuery = _query.value
                )
                if (newSeries.size < pageSize) {
                    isLastPage = true
                }
                if (isSearching) {
                    _series.value = newSeries
                    isSearching = false
                } else {
                    _series.value += newSeries
                }
                currentPage++
            } catch (e: Exception) {
                Log.e("SeriesViewModel", "Error fetching series", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun searchSeries() {
        currentPage = 0
        isLastPage = false
        isSearching = true
        fetchSeries()
    }

    fun debounceSearch() {
        viewModelScope.launch {
            val latestQuery = query.value
            delay(500)
            if (latestQuery == query.value) {
                searchSeries()
            }
        }
    }

    fun refreshSeries(seriesId: Int) {
        val updatedSeriesList = _series.value.mapIndexed{index, series ->
            if (series.id == seriesId)
                series.copy(isFollowing = !series.isFollowing)
            else series
        }
        _series.value = updatedSeriesList
    }


}