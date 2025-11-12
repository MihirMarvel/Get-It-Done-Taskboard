package com.example.getitdone.feature.taskboard.domain

import com.example.getitdone.core.common.AppResult
import com.example.getitdone.feature.taskboard.data.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun observeTasks(): Flow<List<Task>>

    suspend fun get(id: String): Task?

    suspend fun addOrUpdate(
        title: String,
        description: String,
        id: String? = null
    ): AppResult<Unit>

    suspend fun toggleDone(id: String): AppResult<Unit>

    suspend fun delete(id: String): AppResult<Unit>

    suspend fun sync(): AppResult<Unit>
}