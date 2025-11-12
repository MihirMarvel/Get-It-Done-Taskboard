package com.example.getitdone.feature.taskboard.presentation.task_list_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.getitdone.R
import com.example.getitdone.core.ui.theme.backgroundColor
import com.example.getitdone.feature.taskboard.data.Task
import com.example.getitdone.feature.taskboard.presentation.task_list_screen.components.TaskListRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onAdd: () -> Unit,
    onEdit: (String) -> Unit,
    viewModel: TaskListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.events.collect { msg ->
            snackbar.showSnackbar(msg)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.get_it_done)) },
                actions = {
                    IconButton(onClick = viewModel::onSync) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = stringResource(R.string.sync)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFA594F9)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd,
            ) {
                Icon(
                    Icons.Default.Add,
                    stringResource(
                        R.string.add
                    )
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbar) },
        containerColor = backgroundColor
    ) { padding ->
        when (state) {
            TaskboardState.Loading -> Box(
                Modifier
                    .fillMaxSize()
                    .padding(
                        padding
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            TaskboardState.Empty ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.no_tasks_yet_tap_to_add))
                }

            is TaskboardState.Error -> Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text((state as TaskboardState.Error).message)
            }

            is TaskboardState.Success -> {
                val tasks = (state as TaskboardState.Success<List<Task>>).data
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = 12.dp, bottom = 90.dp,
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    items(tasks, key = { it.id }) { task ->
                        TaskListRow(
                            task = task,
                            onToggle = { viewModel.onToggleDone(task.id) },
                            onDelete = { viewModel.onDelete(task.id) },
                            onClick = { onEdit(task.id) }
                        )
                        HorizontalDivider(
                            Modifier.padding(horizontal = 20.dp, vertical = 2.dp),
                            DividerDefaults.Thickness,
                            Color.Gray.copy(alpha = 0.2f),
                        )
                    }
                }
            }
        }
    }
}
