package com.example.getitdone.core.network

data class TaskDto(
    val id: String,
    val title: String,
    val description: String,
    val isDone: Boolean,
    val updatedAt: Long,
)