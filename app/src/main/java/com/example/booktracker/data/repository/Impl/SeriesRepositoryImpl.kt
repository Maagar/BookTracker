package com.example.booktracker.data.repository.Impl

import com.example.booktracker.data.model.FollowedSeries
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.SeriesInfo
import com.example.booktracker.data.model.UserSeriesIds
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.model.VolumeToUpsert
import com.example.booktracker.data.network.SeriesDao
import com.example.booktracker.data.repository.SeriesRepository
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(private val seriesDao: SeriesDao) :
    SeriesRepository {
    override suspend fun getSeries(page: Int, pageSize: Int, searchQuery: String?): List<Series> {
        val offset = page * pageSize
        return seriesDao.getSeriesPaginated(offset, pageSize, searchQuery)

    }

    override suspend fun getFollowedSeries(page: Int, pageSize: Int): List<FollowedSeries> {
        val offset = page * pageSize
        return seriesDao.getFollowedSeries(offset, pageSize)
    }

    override suspend fun getSeriesInfo(seriesId: Int): SeriesInfo {
        return seriesDao.getSeriesInfo(seriesId)
    }

    override suspend fun getVolumes(seriesId: Int): List<Volume> {
        return seriesDao.getAllUserVolumes(seriesId)
    }

    override suspend fun followSeries(seriesId: Int): UserSeriesIds {
        return seriesDao.insertUserSeries(seriesId)
    }

    override suspend fun unfollowSeries(seriesId: Int): Boolean {
        return seriesDao.deleteUserSeries(seriesId)
    }

    override suspend fun upsertUserVolume(volumeToUpsert: VolumeToUpsert): Boolean {
        return seriesDao.upsertUserVolume(volumeToUpsert)
    }
}