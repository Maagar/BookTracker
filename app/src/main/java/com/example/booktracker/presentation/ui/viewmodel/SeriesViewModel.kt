package com.example.booktracker.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.SeriesInfo
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToInsert
import com.example.booktracker.data.model.VolumeToUpdate
import com.example.booktracker.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(private val seriesRepository: SeriesRepository) :
    ViewModel() {

    private val _volumes = MutableStateFlow<List<Volume>>(emptyList())
    val volumes: StateFlow<List<Volume>> = _volumes

    private val _seriesRefreshFlag = MutableStateFlow(false)
    val seriesRefreshFlag: StateFlow<Boolean> = _seriesRefreshFlag

    private val _seriesTabsState = MutableStateFlow(0)
    val seriesTabsState: StateFlow<Int> = _seriesTabsState

    private val _seriesInfo = MutableStateFlow(SeriesInfo())
    val seriesInfo: StateFlow<SeriesInfo> = _seriesInfo

    private val _series = MutableStateFlow<Series?>(null)
    val series: StateFlow<Series?> = _series

    private val _volume = MutableStateFlow<Volume?>(null)
    val volume: StateFlow<Volume?> = _volume

    fun selectVolumeById(volumeId: Int) {
        val selectedVolume = _volumes.value.find { it.id == volumeId }
        if (selectedVolume != null) {
            _volume.value = selectedVolume
        } else {
            Log.e("SeriesViewModel", "Volume with ID $volumeId not found in _volumes.")
        }
    }

    fun onVolumeSelected(seriesId: Int, volumeId: Int, onComplete: () -> Unit) {
        _volumes.value = emptyList()
        viewModelScope.launch {
            fetchVolumes(seriesId)
            _volumes.collect { volumes ->
                if (volumes.isNotEmpty()) {
                    selectVolumeById(volumeId)
                    onComplete()
                    cancel()
                }
            }
        }
    }

    fun selectVolume(index: Int) {
        _volume.value = _volumes.value[index]
    }

    fun clearSelectedVolume() {
        _volume.value = null
    }

    fun selectSeries(series: Series) {
        _series.value = series
    }

    fun clearSelectedSeries() {
        _series.value = null
    }

    fun switchTab(index: Int) {
        _seriesTabsState.value = index
    }

    fun clearVolumeList() {
        _volumes.value = emptyList()
    }

    fun fetchSeriesInfo(seriesId: Int) {
        viewModelScope.launch {
            _seriesInfo.value = seriesRepository.getSeriesInfo(seriesId)
        }
    }

    fun fetchSeries(seriesId: Int) {
        viewModelScope.launch {
            runCatching {
                seriesRepository.getSeriesById(seriesId)
            }.onSuccess { series ->
                _series.value = series
            }.onFailure { exception ->
                Log.e("SeriesViewModel", "Error fetching series", exception)
            }
        }
    }

    fun loadSeriesDetails(series: Series? = null, seriesId: Int) {
        viewModelScope.launch {
            runCatching {
                if (series != null) {
                    _series.value = series
                } else {
                    _series.value = seriesRepository.getSeriesById(seriesId)
                }
                _volumes.value = seriesRepository.getVolumes(seriesId)
                _seriesInfo.value = seriesRepository.getSeriesInfo(seriesId)
            }.onFailure {
                Log.e("SeriesViewModel", "Error loading series details for seriesId $seriesId", it)
            }
        }
    }


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
                onSuccess(result)

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
                onSuccess(result)
                _seriesRefreshFlag.value = true
            } catch (e: Exception) {
                onSuccess(false)
            }
        }
    }

    fun resetRefreshFlag() {
        _seriesRefreshFlag.value = false
    }

    fun onUserVolumeInsert(volumeToInsert: VolumeToInsert) {
        viewModelScope.launch {
            try {
                val result = seriesRepository.insertUserVolume(volumeToInsert)
                refreshVolume(
                    result,
                    volumeToInsert.volume_id,
                    volumeToInsert.times_read,
                    volumeToInsert.owned,
                )
                _seriesRefreshFlag.value = true
            } catch (e: Exception) {
                Log.e("error", "error inserting the volume")
            }
        }
    }

    fun onUserVolumeUpdate(volumeToUpdate: VolumeToUpdate) {
        Log.d("UpdatePayload", volumeToUpdate.toString())
        viewModelScope.launch {
            try {
                val result = seriesRepository.updateUserVolume(volumeToUpdate)
                refreshVolume(
                    volumeToUpdate.id,
                    volumeToUpdate.volume_id,
                    volumeToUpdate.times_read,
                    volumeToUpdate.owned,
                    volumeToUpdate.rating
                )
                _seriesRefreshFlag.value = result
            } catch (e: Exception) {
                Log.e("error", "error updating the volume")
            }
        }
    }

    fun onUserVolumeDelete(userVolumeId: Int, volumeId: Int) {
        viewModelScope.launch {
            try {
                val result = seriesRepository.deleteUserVolume(userVolumeId)
                refreshVolume(
                    userVolumeId,
                    volumeId,
                    0,
                    false,
                )
                _seriesRefreshFlag.value = result

            } catch (e: Exception) {
                Log.e("error", "error deleting the volume")
            }
        }
    }

    private fun refreshVolume(
        userVolumeId: Int?,
        volumeId: Int,
        timesRead: Int,
        owned: Boolean,
        rating: Int? = null
    ) {
        val updatedVolumesList = _volumes.value.map { volume ->
            if (volume.id == volumeId) {
                volume.copy(
                    times_read = timesRead,
                    owned = owned,
                    user_volume_id = if (owned || timesRead > 0) userVolumeId else null,
                    read_date = Clock.System.todayIn(TimeZone.currentSystemDefault()),
                    rating = if (owned || timesRead > 0) rating else null
                )
            } else volume
        }
        _volumes.value = updatedVolumesList
        _volume.value = updatedVolumesList.find { it.id == volumeId }
    }
}
