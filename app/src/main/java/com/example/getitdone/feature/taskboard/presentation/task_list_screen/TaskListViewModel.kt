package com.example.getitdone.feature.taskboard.presentation.task_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getitdone.core.common.AppResult
import com.example.getitdone.feature.taskboard.data.Task
import com.example.getitdone.feature.taskboard.domain.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val repo: TaskRepository,
) : ViewModel() {

    private val _events = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    val state: StateFlow<TaskboardState<List<Task>>> =
        repo.observeTasks()
            .map<List<Task>, TaskboardState<List<Task>>> {
                if (it.isEmpty())
                    TaskboardState.Empty
                else
                    TaskboardState.Success(it)
            }
            .onStart {
                emit(TaskboardState.Loading)
            }
            .catch {
                emit(
                    TaskboardState.Error(
                        it.message ?: "Unexpected error")
                )
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(
                    5_000
                ),
                TaskboardState.Loading
            )

    fun onToggleDone(id: String) = viewModelScope.launch {
        when (val res = repo.toggleDone(id)) {
            is AppResult.Error -> _events.emit(res.message)
            else -> {}
        }
    }

    fun onDelete(id: String) = viewModelScope.launch {
        when (val res = repo.delete(id)) {
            is AppResult.Error -> _events.emit(res.message)
            else -> {}
        }
    }

    fun onSync() = viewModelScope.launch {
        when (val res = repo.sync()) {
            is AppResult.Error -> _events.emit(res.message)
            else -> _events.emit("Synced")
        }
    }
}
