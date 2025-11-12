package com.example.getitdone.feature.taskboard.presentation.edit_task_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getitdone.core.common.AppResult
import com.example.getitdone.feature.taskboard.domain.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val repo: TaskRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val taskId: String? = savedStateHandle["taskId"]

    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var error by mutableStateOf<String?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set

    init {
        if (taskId != null) {
            viewModelScope.launch {
                isLoading = true
                repo.get(taskId)?.let {
                    title = it.title
                    description = it.description
                }
                isLoading = false
            }
        }
    }

    fun updateTitle(t: String) {
        title = t
    }

    fun updateDescription(d: String) {
        description = d
    }

    suspend fun save(): Boolean {
        isLoading = true
        val res = repo.addOrUpdate(title, description, taskId)
        isLoading = false
        return when (res) {
            is AppResult.Success -> true
            is AppResult.Error -> {
                error = res.message
                false
            }
        }
    }
}
