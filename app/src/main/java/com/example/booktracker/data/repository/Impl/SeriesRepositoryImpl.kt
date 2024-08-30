package com.example.booktracker.data.repository.Impl

import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.network.SeriesDao
import com.example.booktracker.data.repository.SeriesRepository
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(private val seriesDao: SeriesDao) :
    SeriesRepository {
    override suspend fun getSeries(page: Int, pageSize: Int, searchQuery: String?): List<Series> {
        val offset = page * pageSize
        return seriesDao.getSeriesPaginated(offset, pageSize, searchQuery)

    }

    override suspend fun getVolumes(seriesId: Int): List<Volume> {
        return seriesDao.getAllUserVolumes(seriesId)
    }
}