package com.example.getitdone.feature.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.getitdone.feature.taskboard.presentation.edit_task_screen.EditTaskScreen
import com.example.getitdone.feature.taskboard.presentation.task_list_screen.TaskListScreen

@Composable
fun TaskboardNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val animSpec = tween<IntOffset>(durationMillis = 500)

    NavHost(
        navController = navController,
        startDestination = Destinations.LIST,
        modifier = modifier,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it }, animationSpec = animSpec)
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it }, animationSpec = animSpec)
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it }, animationSpec = animSpec)
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it }, animationSpec = animSpec)
        }

    ) {
        composable(Destinations.LIST) {
            TaskListScreen(
                onAdd = { navController.navigate(Destinations.EDIT) },
                onEdit = { id ->
                    navController.navigate(
                        "${Destinations.EDIT}?${Destinations.EDIT_ARG}=$id"
                    )
                }
            )
        }
        composable(
            route = "${Destinations.EDIT}?${Destinations.EDIT_ARG}={${Destinations.EDIT_ARG}}",
            arguments = listOf(
                navArgument(Destinations.EDIT_ARG) {
                    nullable = true
                }
            )
        ) {
            EditTaskScreen(onSaved = { navController.popBackStack() })
        }
    }
}
