package com.example.booktracker.data.repository

import com.example.booktracker.data.model.ProfileData

interface ProfileRepository {
    suspend fun getProfileData(): ProfileData
}