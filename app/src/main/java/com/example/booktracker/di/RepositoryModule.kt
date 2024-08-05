package com.example.booktracker.di

import com.example.booktracker.data.repository.AuthenticationRepository
import com.example.booktracker.data.repository.Impl.AuthenticationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthenticateRepository(impl: AuthenticationRepositoryImpl) : AuthenticationRepository
}