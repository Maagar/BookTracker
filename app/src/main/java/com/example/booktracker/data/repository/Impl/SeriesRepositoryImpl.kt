package com.example.booktracker.data.repository.Impl

import com.example.booktracker.data.model.FollowedSeries
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.SeriesInfo
import com.example.booktracker.data.model.UpcomingVolume
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToInsert
import com.example.booktracker.data.model.VolumeToUpdate
import com.example.booktracker.data.network.SeriesDao
import com.example.booktracker.data.repository.SeriesRepository
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(private val seriesDao: SeriesDao) :
    SeriesRepository {

    override suspend fun getSeries(page: Int, pageSize: Int, searchQuery: String): List<Series> {
        val offset = page * pageSize
        return seriesDao.getSeriesPaginated(offset, pageSize, searchQuery)
    }

    override suspend fun getFollowedSeries(
        page: Int,
        pageSize: Int,
        sortByDate: Boolean,
        showFinished: Boolean
    ): List<FollowedSeries> {
        val offset = page * pageSize
        return seriesDao.getFollowedSeries(offset, pageSize, sortByDate, showFinished)
    }

    override suspend fun getSeriesInfo(seriesId: Int): SeriesInfo {
        return seriesDao.getSeriesInfo(seriesId)
    }

    override suspend fun getSeriesById(seriesId: Int): Series {
        return seriesDao.getSeries(seriesId)
    }

    override suspend fun getVolumes(seriesId: Int): List<Volume> {
        return seriesDao.getAllUserVolumes(seriesId)
    }

    override suspend fun getVolume(volumeId: Int): Volume {
        return seriesDao.getVolumeById(volumeId)
    }

    override suspend fun followSeries(seriesId: Int): Boolean {
        return seriesDao.insertUserSeries(seriesId)
    }

    override suspend fun unfollowSeries(seriesId: Int): Boolean {
        return seriesDao.deleteUserSeries(seriesId)
    }

    override suspend fun insertUserVolume(volumeToInsert: VolumeToInsert): Int? {
        return seriesDao.insertUserVolume(volumeToInsert)
    }

    override suspend fun updateUserVolume(volumeToUpdate: VolumeToUpdate): Boolean {
        return seriesDao.updateUserVolume(volumeToUpdate)
    }

    override suspend fun deleteUserVolume(userVolumeId: Int): Boolean {
        return seriesDao.deleteUserVolume(userVolumeId)
    }

    override suspend fun getUpcomingVolumes(page: Int, pageSize: Int): List<UpcomingVolume> {
        val offset = page * pageSize
        return seriesDao.getUpcomingVolumes(offset, pageSize)
    }

    override suspend fun getRecommendedSeries(): List<Series> {
        return seriesDao.getRecommendedSeries()
    }
}