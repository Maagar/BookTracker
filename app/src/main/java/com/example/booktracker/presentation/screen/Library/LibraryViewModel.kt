package com.example.booktracker.presentation.screen.Library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.model.UserSeries
import com.example.booktracker.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(private val seriesRepository: SeriesRepository) :
    ViewModel() {

    private val _userSeries = MutableStateFlow<List<UserSeries>>(emptyList())
    val userSeries: StateFlow<List<UserSeries>> = _userSeries

    private var currentPage = 0
    private val pageSize = 20
    private var isLoading = false
    private var isLastPage = false

    init {
        fetchFollowedSeries()
    }

    fun fetchFollowedSeries() {
        if (isLoading || isLastPage) return

        viewModelScope.launch {
            isLoading = true
            try {
                val newSeries =
                    seriesRepository.getFollowedSeries(page = currentPage, pageSize = pageSize)
                if (newSeries.size < pageSize) {
                    isLastPage = true
                }
                _userSeries.value += newSeries
                currentPage++
            } catch (e: Exception) {
                Log.e("SeriesViewModel", "Error fetching series", e)
            } finally {
                isLoading = false
            }
        }
    }
}