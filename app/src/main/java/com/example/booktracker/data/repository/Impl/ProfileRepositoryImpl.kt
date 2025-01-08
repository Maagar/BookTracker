package com.example.booktracker.data.repository.Impl

import com.example.booktracker.data.model.ProfileData
import com.example.booktracker.data.model.Statistics
import com.example.booktracker.data.network.ProfileDao
import com.example.booktracker.data.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val profileDao: ProfileDao) : ProfileRepository {
    override suspend fun getProfileData(): ProfileData {
        return profileDao.getProfileData()
    }

    override suspend fun getStatistics(): Statistics {
        return profileDao.getStatistics()
    }
}