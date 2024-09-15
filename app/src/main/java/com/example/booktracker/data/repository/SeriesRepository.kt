package com.example.booktracker.data.repository

import com.example.booktracker.data.model.FollowedSeries
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.SeriesInfo
import com.example.booktracker.data.model.UserSeriesIds
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToInsert
import com.example.booktracker.data.model.VolumeToUpdate

interface SeriesRepository {

    suspend fun getSeries(page: Int, pageSize: Int, searchQuery: String? = null): List<Series>
    suspend fun getFollowedSeries(page: Int, pageSize: Int): List<FollowedSeries>
    suspend fun getSeriesInfo(seriesId: Int): SeriesInfo
    suspend fun getVolumes(seriesId: Int): List<Volume>
    suspend fun followSeries(seriesId: Int): UserSeriesIds
    suspend fun unfollowSeries(seriesId: Int): Boolean
    suspend fun insertUserVolume(volumeToInsert: VolumeToInsert): Boolean
    suspend fun updateUserVolume(volumeToUpdate: VolumeToUpdate): Boolean
    suspend fun deleteUserVolume(userVolumeId: Int): Boolean

}