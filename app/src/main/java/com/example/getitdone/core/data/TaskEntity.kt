package com.example.getitdone.core.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val isDone: Boolean,
    val updatedAt: Long = Instant.now().toEpochMilli(),
)
