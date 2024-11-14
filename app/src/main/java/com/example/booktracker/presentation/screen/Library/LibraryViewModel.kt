package com.example.booktracker.presentation.screen.Library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.model.FollowedSeries
import com.example.booktracker.data.model.UpcomingVolume
import com.example.booktracker.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(private val seriesRepository: SeriesRepository) :
    ViewModel() {

    private val _userSeries = MutableStateFlow<List<FollowedSeries>>(emptyList())
    val userSeries: StateFlow<List<FollowedSeries>> = _userSeries

    private val _upcomingVolumes = MutableStateFlow<List<UpcomingVolume>>(emptyList())
    val upcomingVolumes: StateFlow<List<UpcomingVolume>> = _upcomingVolumes

    private val _libraryTabsState = MutableStateFlow(0)
    val libraryTabsState: StateFlow<Int> = _libraryTabsState

    private var currentPage = 0
    private val pageSize = 20
    private var isLoading = false
    private var isLastPage = false

    private var currentPageUpcoming = 0
    private val pageSizeUpcoming = 10
    private var isLoadingUpcoming = false
    private var isLastPageUpcoming = false

    init {
        fetchFollowedSeries()
        fetchUpcomingSeries()
    }

    fun switchTab(index: Int) {
        _libraryTabsState.value = index
    }

    fun refreshSeries() {
        _userSeries.value = emptyList()
        isLastPage = false
        currentPage = 0
        fetchFollowedSeries()
    }

    fun fetchUpcomingSeries() {
        if (isLoadingUpcoming || isLastPageUpcoming) return

        viewModelScope.launch {
            isLoading = true
            runCatching {
                seriesRepository.getUpcomingVolumes(currentPageUpcoming, pageSizeUpcoming)
            }.onSuccess { newVolumes ->
                if (newVolumes.size < pageSizeUpcoming) {
                    isLastPageUpcoming = true
                }
                _upcomingVolumes.value += newVolumes
                currentPage++
            }.onFailure { e ->
                Log.e("LibraryViewModel", "Error fetching upcoming volumes", e)
            }.also {
                isLoadingUpcoming = false
            }
        }
    }

    fun fetchFollowedSeries() {
        if (isLoading || isLastPage) return

        viewModelScope.launch {
            isLoading = true
            runCatching {
                seriesRepository.getFollowedSeries(page = currentPage, pageSize = pageSize)
            }.onSuccess { newSeries ->
                if (newSeries.size < pageSize) {
                    isLastPage = true
                }
                _userSeries.value += newSeries
                currentPage++
            }.onFailure { e ->
                Log.e("SeriesViewModel", "Error fetching series", e)
            }.also {
                isLoading = false
            }
        }
    }
}