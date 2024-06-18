package com.example.todoappcompose.addtask.domain

import com.example.todoappcompose.addtask.data.TodoRepository
import com.example.todoappcompose.addtask.ui.model.TaskModel
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(private val taskRepository: TodoRepository) {

    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.update(taskModel)
    }

}