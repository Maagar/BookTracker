package com.example.booktracker.di

import com.example.booktracker.data.repository.Impl.SeriesRepositoryImpl
import com.example.booktracker.data.repository.SeriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class SeriesRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSeriesRepository(impl: SeriesRepositoryImpl) : SeriesRepository
}