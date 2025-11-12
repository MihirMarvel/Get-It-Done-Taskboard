package com.example.getitdone.core.network

interface TaskService {
    suspend fun fetchTasks(): List<TaskDto>
}