package com.example.booktracker.presentation.screen.Profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.model.ProfileData
import com.example.booktracker.data.model.Statistics
import com.example.booktracker.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) :
    ViewModel() {

    private val _profileData = MutableStateFlow<ProfileData?>(null)
    val profileData: StateFlow<ProfileData?> = _profileData

    private val _stats = MutableStateFlow<Statistics?>(null)
    val stats: StateFlow<Statistics?> = _stats

    init {
        viewModelScope.launch {
            _profileData.value = profileRepository.getProfileData()
            _stats.value = profileRepository.getStatistics()
            Log.d("test", "${_stats.value}")
        }

    }

}