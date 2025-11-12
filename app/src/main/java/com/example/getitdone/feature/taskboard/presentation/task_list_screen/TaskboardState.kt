package com.example.getitdone.feature.taskboard.presentation.task_list_screen

sealed interface TaskboardState<out T> {

    data object Loading : TaskboardState<Nothing>

    data object Empty : TaskboardState<Nothing>

    data class Success<T>(val data: T) : TaskboardState<T>

    data class Error(val message: String) : TaskboardState<Nothing>
}