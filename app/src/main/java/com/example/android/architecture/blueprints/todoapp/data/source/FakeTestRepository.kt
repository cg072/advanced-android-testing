package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.runBlocking

class FakeTestRepository: TasksRepository {

    private var tasksServiceData: LinkedHashMap<String, Task> = LinkedHashMap()

    private val observableTasks = MutableLiveData<Result<List<Task>>>()
    private val observableTask = MutableLiveData<Result<Task>>()

    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {
        return Result.Success(tasksServiceData.values.toList())
    }

    override suspend fun refreshTasks() {
        observableTasks.value = getTasks()
    }

    override suspend fun refreshTask(taskId: String) {
        tasksServiceData.values.find { it.id == taskId }?.let { observableTask.value = Result.Success(it) }
    }

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        runBlocking { refreshTasks() }
        return observableTasks
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        runBlocking { refreshTask(taskId) }
        return observableTask
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
        return tasksServiceData[taskId]?.let { Result.Success(it) } ?: Result.Error(Exception())
    }

    override suspend fun saveTask(task: Task) {
        tasksServiceData[task.id] = task
    }

    override suspend fun completeTask(task: Task) {
        val completedTask = task.copy(isCompleted = true)
        tasksServiceData[task.id] = completedTask
        refreshTasks()
    }

    override suspend fun completeTask(taskId: String) {
        tasksServiceData[taskId]?.isCompleted = true
        refreshTask(taskId)
    }

    override suspend fun activateTask(task: Task) {
        val activateTask = task.copy(isCompleted = false)
        tasksServiceData[task.id] = activateTask
        refreshTasks()
    }

    override suspend fun activateTask(taskId: String) {
        tasksServiceData.values.find { it.id == taskId}?.isCompleted = false
        refreshTask(taskId)
    }

    override suspend fun clearCompletedTasks() {
        tasksServiceData.clear()
        refreshTasks()
    }

    override suspend fun deleteAllTasks() {
        tasksServiceData.values.filter { it.isCompleted }.map { it.id }.forEach { tasksServiceData.remove(it) }
        refreshTasks()
    }

    override suspend fun deleteTask(taskId: String) {
        tasksServiceData.remove(taskId)
    }

    fun getTasks(): Result<List<Task>> {
        return Result.Success(tasksServiceData.values.toList())
    }

    fun addTasks(vararg tasks: Task) {
        for (task in tasks) {
            tasksServiceData[task.id] = task
        }
        runBlocking { refreshTasks() }
    }
}