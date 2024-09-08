package com.example.booktracker.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(private val seriesRepository: SeriesRepository) :
    ViewModel() {

    private val _volumes = MutableStateFlow<List<Volume>>(emptyList())
    val volumes: StateFlow<List<Volume>> = _volumes

    private val _seriesRefreshFlag = MutableStateFlow(false)
    val seriesRefreshFlag: StateFlow<Boolean> = _seriesRefreshFlag

    fun fetchVolumes(seriesId: Int) {
        viewModelScope.launch {
            try {
                val volumeList = seriesRepository.getVolumes(seriesId)
                _volumes.value = volumeList
            } catch (e: Exception) {
                Log.e("SeriesViewModel", "Error fetching volumes", e)
            }
        }
    }

    fun onFollowSeries(seriesId: Int, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val result = seriesRepository.followSeries(seriesId)
                onSuccess(true)
                Log.d("onFollowSeries", "Refresh Flag: $_seriesRefreshFlag")

                _seriesRefreshFlag.value = true
            } catch (e: Exception) {
                onSuccess(false)
            }
        }
    }

    fun onUnfollowSeries(seriesId: Int, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val result = seriesRepository.unfollowSeries(seriesId)
                onSuccess(true)
                _seriesRefreshFlag.value = true
                Log.d("onUnFollowSeries", "Refresh Flag: $_seriesRefreshFlag")
            } catch (e: Exception) {
                onSuccess(false)
            }
        }
    }

    fun resetRefreshFlag() {
        _seriesRefreshFlag.value = false
        Log.d("resetRefreshFlag", "Refresh Flag: ${_seriesRefreshFlag.value}")
    }

}
