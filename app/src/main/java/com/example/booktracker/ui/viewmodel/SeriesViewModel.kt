package com.example.booktracker.ui.viewmodel

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

    private val _dialogState = MutableStateFlow(0)
    val dialogState: StateFlow<Int> = _dialogState

    private val _seriesInfo = MutableStateFlow(SeriesInfo())
    val seriesInfo: StateFlow<SeriesInfo> = _seriesInfo

    private val _series = MutableStateFlow<Series?>(null)
    val series: StateFlow<Series?> = _series

    fun selectSeries(series: Series) {
        _series.value = series
    }

    fun clearSelectedSeries() {
        _series.value = null
    }

    fun switchTab(index: Int) {
        _dialogState.value = index
    }

    fun clearVolumeList() {
        _volumes.value = emptyList()
    }

    fun fetchSeriesInfo(seriesId: Int) {
        viewModelScope.launch {
            _seriesInfo.value = seriesRepository.getSeriesInfo(seriesId)
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
        viewModelScope.launch {
            try {
                val result = seriesRepository.updateUserVolume(volumeToUpdate)
                refreshVolume(
                    volumeToUpdate.id,
                    volumeToUpdate.volume_id,
                    volumeToUpdate.times_read,
                    volumeToUpdate.owned,
                )
                _seriesRefreshFlag.value = result
            } catch (e: Exception) {
                Log.e("error", "error updating the volume")
            }
        }
    }

    fun onUserVolumeDelete(volumeId: Int, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val result = seriesRepository.deleteUserVolume(volumeId)
                onSuccess(result)
                _seriesRefreshFlag.value = true
            } catch (e: Exception) {
                onSuccess(false)
            }
        }
    }

    private fun refreshVolume(userVolumeId: Int?, volumeId: Int, timesRead: Int, owned: Boolean) {
        val updatedVolumesList = _volumes.value.mapIndexed { index, volume ->
            if (volume.id == volumeId) {
                volume.copy(
                    times_read = timesRead,
                    owned = owned,
                    user_volume_id = userVolumeId,
                    read_date = Clock.System.todayIn(TimeZone.currentSystemDefault())
                )
            } else volume
        }
        _volumes.value = updatedVolumesList
    }

}
