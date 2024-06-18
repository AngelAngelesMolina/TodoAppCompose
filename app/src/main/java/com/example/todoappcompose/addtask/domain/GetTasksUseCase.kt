package com.example.todoappcompose.addtask.domain

import com.example.todoappcompose.addtask.data.TodoRepository
import com.example.todoappcompose.addtask.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val tasksRepository: TodoRepository) {

    operator fun invoke(): Flow<List<TaskModel>> {
        return tasksRepository.tasks
    }


}