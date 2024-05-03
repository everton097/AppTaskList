package com.example.tasklist.ui.views

import com.example.tasklist.models.Task

data class TaskListUiState(
    val taskList: List<Task> = listOf()
)
