package com.example.getitdone.core.network

import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DummyTaskServiceImpl @Inject constructor() : TaskService {

    override suspend fun fetchTasks(): List<TaskDto> {

        delay(1200)

        val now = System.currentTimeMillis()
        return listOf(
            TaskDto(
                "dummmy-1",
                "Read spec",
                "Fill up application form",
                false,
                now - 86_400_000
            ),
            TaskDto(
                "dummmy-2",
                "Design",
                "Complete work on GetItDone App",
                true,
                now - 60_200_000
            ),
            TaskDto(
                "dummmy-3",
                "Build",
                "Build personal app",
                false,
                now - 34_600_000
            ),
            TaskDto(
                "dummmy-4",
                "Write",
                "Write a blog for Linkedin",
                false,
                now - 16_600_000
            ),
            TaskDto(
                "dummmy-5",
                "Shopping",
                "Grocery Shopping",
                false,
                now - 3_600_000
            )
        )
    }

}