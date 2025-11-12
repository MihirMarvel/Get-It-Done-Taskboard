package com.example.getitdone.core.di

import com.example.getitdone.core.network.DummyTaskServiceImpl
import com.example.getitdone.core.network.TaskService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideDummyService(): TaskService = DummyTaskServiceImpl()

}