package com.example.getitdone.feature.taskboard.data

import android.content.Context
import com.example.getitdone.R
import com.example.getitdone.core.common.AppResult
import com.example.getitdone.core.data.TaskDao
import com.example.getitdone.core.data.TaskEntity
import com.example.getitdone.core.network.TaskService
import com.example.getitdone.feature.taskboard.domain.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao,
    private val network: TaskService,
    private val context: Context,
) : TaskRepository {

    override fun observeTasks(): Flow<List<Task>> =
        dao.observeAll().map { it.map(TaskEntity::toDomain) }

    override suspend fun get(
        id: String,
    ): Task? = dao.getById(id)?.toDomain()

    override suspend fun addOrUpdate(
        title: String,
        description: String,
        id: String?,
    ): AppResult<Unit> = runCatching {
        val now = System.currentTimeMillis()
        val entity = TaskEntity(
            id = id ?: UUID.randomUUID().toString(),
            title = title.trim(),
            description = description.trim(),
            isDone = dao.getById(id ?: "")?.isDone ?: false,
            updatedAt = now
        )
        require(entity.title.isNotEmpty()) { 
            context.getString(R.string.title_cannot_be_empty) 
        }
        dao.upsert(entity)
    }.fold(
        onSuccess = { AppResult.Success(Unit) },
        onFailure = {
            AppResult.Error(
                it.message ?: context.getString(
                    R.string.failed_to_save
                ),
                it
            )
        }
    )

    override suspend fun toggleDone(id: String): AppResult<Unit> =
        runCatching {
            val current = dao.getById(id) ?: return AppResult.Error(
                context.getString(R.string.task_not_found)
            )
            dao.upsert(
                current.copy(
                    isDone = !current.isDone,
                    updatedAt = System.currentTimeMillis()
                )
            )
        }.fold(
            onSuccess = { AppResult.Success(Unit) },
            onFailure = {
                AppResult.Error(
                    it.message ?: context.getString(
                        R.string.failed_to_toggle
                    ),
                    it
                )
            }
        )

    override suspend fun delete(id: String): AppResult<Unit> =
        runCatching { dao.deleteById(id) }.fold(
            onSuccess = { AppResult.Success(Unit) },
            onFailure = {
                AppResult.Error(
                    it.message ?: context.getString(
                        R.string.failed_to_delete
                    ),
                    it
                )
            }
        )

    override suspend fun sync(): AppResult<Unit> =
        runCatching {
            val remote = network.fetchTasks().map { it.toEntity() }
            dao.upsertAll(remote)
        }.fold(
            onSuccess = { AppResult.Success(Unit) },
            onFailure = {
                AppResult.Error(
                    context.getString(
                        R.string.sync_failed
                    ),
                    it
                )
            }
        )
}
