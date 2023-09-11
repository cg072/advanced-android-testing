package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.`is`
import org.junit.Assert
import org.junit.Test


class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        // Create an active task
        val tasks: List<Task> = listOf(Task("title", "desc", isCompleted = false))
        // Call your function
        val result = getActiveAndCompletedStats(tasks)
        // Check the result

        MatcherAssert.assertThat(result.activeTasksPercent, `is`(100f))
        MatcherAssert.assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_noActive_returnsHundredZero() {
        val task = listOf(Task("title", "desc", isCompleted = true))
        val result = getActiveAndCompletedStats(task)

        MatcherAssert.assertThat(result.activeTasksPercent, `is`(0f))
        MatcherAssert.assertThat(result.completedTasksPercent, `is`(100f))
    }

    @Test
    fun gatActiveAndCompletedStats_40percentActive_returnsHundredForty() {
        val task = listOf(
            Task("title", "desc", isCompleted = false),
            Task("title", "desc", isCompleted = false),
            Task("title", "desc", isCompleted = false),
            Task("title", "desc", isCompleted = false),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
        )
        val result = getActiveAndCompletedStats(task)

        MatcherAssert.assertThat(result.activeTasksPercent, `is`(40f))
        MatcherAssert.assertThat(result.completedTasksPercent, `is`(60f))
    }

    @Test
    fun getActiveAndCompletedStats_emptyTask_returnHundredZeroBothActiveAndCompleted() {
        val task = listOf<Task>()
        val result = getActiveAndCompletedStats(task)

        MatcherAssert.assertThat(result.activeTasksPercent, `is`(0f))
        MatcherAssert.assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_error_returnHundredZeroBothActiveAndCompleted() {
        val task = null
        val result = getActiveAndCompletedStats(task)

        MatcherAssert.assertThat(result.activeTasksPercent, `is`(0f))
        MatcherAssert.assertThat(result.completedTasksPercent, `is`(0f))
    }
}