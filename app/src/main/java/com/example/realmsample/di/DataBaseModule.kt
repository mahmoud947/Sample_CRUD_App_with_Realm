package com.example.realmsample.di

import com.example.data.database.RealmDatabase
import com.example.data.repositorys.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideRepository(): Repository = Repository.getInstance()
}