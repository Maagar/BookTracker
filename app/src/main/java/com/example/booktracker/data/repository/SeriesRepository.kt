package com.example.booktracker.data.repository

import com.example.booktracker.data.model.FollowedSeries
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.SeriesInfo
import com.example.booktracker.data.model.UpcomingVolume
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToInsert
import com.example.booktracker.data.model.VolumeToUpdate

interface SeriesRepository {
    suspend fun getSeries(page: Int, pageSize: Int, searchQuery: String): List<Series>
    suspend fun getFollowedSeries(page: Int, pageSize: Int, sortByDate: Boolean, showFinished: Boolean): List<FollowedSeries>
    suspend fun getSeriesInfo(seriesId: Int): SeriesInfo
    suspend fun getSeriesById(seriesId: Int): Series
    suspend fun getVolumes(seriesId: Int): List<Volume>
    suspend fun getVolume(volumeId: Int): Volume
    suspend fun followSeries(seriesId: Int): Boolean
    suspend fun unfollowSeries(seriesId: Int): Boolean
    suspend fun insertUserVolume(volumeToInsert: VolumeToInsert): Int?
    suspend fun updateUserVolume(volumeToUpdate: VolumeToUpdate): Boolean
    suspend fun deleteUserVolume(userVolumeId: Int): Boolean
    suspend fun getUpcomingVolumes(page: Int, pageSize: Int): List<UpcomingVolume>
    suspend fun getRecommendedSeries(): List<Series>

}