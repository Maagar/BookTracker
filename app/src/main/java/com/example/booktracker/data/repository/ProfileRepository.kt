package com.example.booktracker.data.repository

import com.example.booktracker.data.model.ProfileData
import com.example.booktracker.data.model.Statistics

interface ProfileRepository {
    suspend fun getProfileData(): ProfileData
    suspend fun getStatistics(): Statistics
}