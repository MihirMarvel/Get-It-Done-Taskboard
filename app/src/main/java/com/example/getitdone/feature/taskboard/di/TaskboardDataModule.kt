package com.example.getitdone.feature.taskboard.di

import android.content.Context
import com.example.getitdone.core.data.TaskDao
import com.example.getitdone.core.network.TaskService
import com.example.getitdone.feature.taskboard.data.TaskRepositoryImpl
import com.example.getitdone.feature.taskboard.domain.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskboardDataModule {

    @Provides
    @Singleton
    fun provideTaskRepository(
        dao: TaskDao,
        service: TaskService,
        @ApplicationContext ctx: Context
    ): TaskRepository = TaskRepositoryImpl(
        dao,
        network = service,
        context = ctx,
    )

}