package com.example.booktracker.data.repository

import com.example.booktracker.data.model.Series

interface SeriesRepository{

    suspend fun getSeries(): List<Series>
}