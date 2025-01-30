package com.example.booktracker.presentation.screen.Library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.local.UserPreferences
import com.example.booktracker.data.model.FollowedSeries
import com.example.booktracker.data.model.UpcomingVolume
import com.example.booktracker.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val userPreferences: UserPreferences
) :
    ViewModel() {

    private val _userSeries = MutableStateFlow<List<FollowedSeries>>(emptyList())
    val userSeries: StateFlow<List<FollowedSeries>> = _userSeries

    private val _upcomingVolumes = MutableStateFlow<List<UpcomingVolume>>(emptyList())
    val upcomingVolumes: StateFlow<List<UpcomingVolume>> = _upcomingVolumes

    private val _libraryTabsState = MutableStateFlow(0)
    val libraryTabsState: StateFlow<Int> = _libraryTabsState

    private val _isFollowedRefreshing = MutableStateFlow(false)
    val isFollowedRefreshing: StateFlow<Boolean> = _isFollowedRefreshing
    private val _isUpcomingRefreshing = MutableStateFlow(false)
    val isUpcomingRefreshing: StateFlow<Boolean> = _isUpcomingRefreshing

    private var currentPage = 0
    private val pageSize = 20
    private var isLoading = false
    private var isLastPage = false

    private var currentPageUpcoming = 0
    private val pageSizeUpcoming = 10
    private var isLoadingUpcoming = false
    private var isLastPageUpcoming = false

    private val _sortByDate = MutableStateFlow(true)
    val sortByDate: StateFlow<Boolean> = _sortByDate

    private val _showFinished = MutableStateFlow(false)
    val showFinished: StateFlow<Boolean> = _showFinished

    init {
        viewModelScope.launch {
            _sortByDate.value = userPreferences.sortByDate.first()
            _showFinished.value = userPreferences.showFinished.first()
            fetchFollowedSeries()
            fetchUpcomingSeries()
        }
    }

    fun startFollowedRefreshing() {
        _isFollowedRefreshing.value = true
    }

    fun startUpcomingRefreshing() {
        _isUpcomingRefreshing.value = true
    }


    fun switchTab(index: Int) {
        _libraryTabsState.value = index
    }

    fun refreshFollowedSeries() {
        viewModelScope.launch {
            _userSeries.value = emptyList()
            isLastPage = false
            currentPage = 0
            fetchFollowedSeries()
        }
    }

    fun refreshUpcomingSeries() {
        _upcomingVolumes.value = emptyList()
        isLastPageUpcoming = false
        currentPageUpcoming = 0
        fetchUpcomingSeries()
    }

    fun fetchUpcomingSeries() {
        if (isLoadingUpcoming || isLastPageUpcoming) return

        viewModelScope.launch {
            isLoadingUpcoming = true
            runCatching {
                seriesRepository.getUpcomingVolumes(currentPageUpcoming, pageSizeUpcoming)
            }.onSuccess { newVolumes ->
                if (newVolumes.size < pageSizeUpcoming) {
                    isLastPageUpcoming = true
                }
                _upcomingVolumes.value += newVolumes
                currentPageUpcoming++
            }.onFailure { e ->
                Log.e("LibraryViewModel", "Error fetching upcoming volumes", e)
            }.also {
                isLoadingUpcoming = false
                _isUpcomingRefreshing.value = false
            }
        }
    }

    fun updateSorting(sortByDate: Boolean) {
        _sortByDate.value = sortByDate
        viewModelScope.launch {
            userPreferences.saveSortBy(sortByDate)
            refreshFollowedSeries()
        }
    }

    fun updateShowFinished(showFinished: Boolean) {
        _showFinished.value = showFinished
        viewModelScope.launch {
            userPreferences.saveShowFinished(showFinished)
            refreshFollowedSeries()
        }
    }

    fun fetchFollowedSeries() {
        if (isLoading || isLastPage) return

        viewModelScope.launch {
            isLoading = true
            runCatching {
                seriesRepository.getFollowedSeries(
                    page = currentPage,
                    pageSize = pageSize,
                    sortByDate = _sortByDate.value,
                    showFinished = _showFinished.value
                )
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
                _isFollowedRefreshing.value = false
            }
        }
    }
}