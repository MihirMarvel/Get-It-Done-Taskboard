package com.example.getitdone.core.di

import android.content.Context
import androidx.room.Room
import com.example.getitdone.core.data.AppDatabase
import com.example.getitdone.core.data.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(
            ctx,
            AppDatabase::class.java,
            "taskboard.db"
        ).build()

    @Provides
    fun provideTaskDao(
        db: AppDatabase,
    ): TaskDao = db.taskDao()

}