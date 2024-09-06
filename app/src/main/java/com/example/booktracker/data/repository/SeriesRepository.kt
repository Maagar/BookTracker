package com.example.booktracker.data.repository

import com.example.booktracker.data.model.FollowedSeries
import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.UserSeriesIds
import com.example.booktracker.data.model.Volume

interface SeriesRepository {

    suspend fun getSeries(page: Int, pageSize: Int, searchQuery: String? = null): List<Series>
    suspend fun getFollowedSeries(page: Int, pageSize: Int): List<FollowedSeries>
    suspend fun getVolumes(seriesId: Int): List<Volume>
    suspend fun followSeries(seriesId: Int): UserSeriesIds
    suspend fun unfollowSeries(seriesId: Int): Boolean
}