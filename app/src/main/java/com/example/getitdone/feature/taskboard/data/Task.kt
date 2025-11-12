package com.example.getitdone.feature.taskboard.data

import com.example.getitdone.core.data.TaskEntity
import com.example.getitdone.core.network.TaskDto

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val isDone: Boolean,
    val updatedAt: Long,
)

fun TaskEntity.toDomain() = Task(id, title, description, isDone, updatedAt)

fun Task.toEntity() = TaskEntity(id, title, description, isDone, updatedAt)

fun TaskDto.toEntity() = TaskEntity(id, title, description, isDone, updatedAt)
