package com.example.booktracker.data.repository

import com.example.booktracker.data.model.Series
import com.example.booktracker.data.model.Volume

interface SeriesRepository{

    suspend fun getSeries(): List<Series>
    suspend fun getVolumes(seriesId: Int): List<Volume>
}