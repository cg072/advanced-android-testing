package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task

class FakeDataSource(var tasks: MutableList<Task>? = mutableListOf()): TasksDataSource {
    override fun observeTasks(): LiveData<Result<List<Task>>> {
        TODO("Not yet implemented")
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTasks(): Result<List<Task>> = tasks?.let { Result.Success(it) } ?: Result.Error(Exception())

    override suspend fun getTask(taskId: String): Result<Task> = tasks?.find { it.id == taskId}?.let { Result.Success(it) } ?: Result.Error(Exception())

    override suspend fun refreshTasks() {
        //NO-OP
    }

    override suspend fun refreshTask(taskId: String) {
        //NO-OP
    }

    override suspend fun saveTask(task: Task) {
        tasks?.add(task)
    }

    override suspend fun completeTask(task: Task) {
        tasks?.find { it.id == task.id}?.isCompleted = true
    }

    override suspend fun completeTask(taskId: String) {
        tasks?.find { it.id == taskId}?.isCompleted = true
    }

    override suspend fun activateTask(task: Task) {
        tasks?.find { it.id == task.id}?.isCompleted = false
    }

    override suspend fun activateTask(taskId: String) {
        tasks?.find { it.id == taskId}?.isCompleted = false
    }

    override suspend fun clearCompletedTasks() {
        tasks?.removeAll { it.isCompleted }
    }

    override suspend fun deleteAllTasks() {
        tasks?.clear()
    }

    override suspend fun deleteTask(taskId: String) {
        tasks?.removeIf { it.id == taskId }
    }
}