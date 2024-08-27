package com.example.booktracker.data.repository.Impl

import android.util.Log
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.Volume
import com.example.booktracker.data.network.SeriesDao
import com.example.booktracker.data.repository.SeriesRepository
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(private val seriesDao: SeriesDao) :
    SeriesRepository {
    override suspend fun getSeries(): List<Series> {
        val seriesList = seriesDao.getAllSeries()
        Log.d("SeriesRepository", "Series from DAO: $seriesList")
        return seriesList
    }

    override suspend fun getVolumes(seriesId: Int): List<Volume> {
        return seriesDao.getAllUserVolumes(seriesId)
    }
}