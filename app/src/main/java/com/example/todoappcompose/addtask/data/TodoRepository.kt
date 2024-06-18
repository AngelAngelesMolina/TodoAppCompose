package com.example.todoappcompose.addtask.data

import com.example.todoappcompose.addtask.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TodoRepository @Inject constructor(private val taskDao: TaskDao) {
    //clase que hace todas las consultas a la bd o a internet

    val tasks: Flow<List<TaskModel>> =
        taskDao.getTasks().map {    //mapper recorre el listado pero devuelve el lista que recorre
                items ->
            items.map {
                TaskModel(it.id, it.task, it.selected)
            }
        }


    suspend fun add(taskModel: TaskModel) {
        taskDao.addTask(TaskEntity(taskModel.id, taskModel.task, taskModel.selected))
    }

    suspend fun update(taskModel: TaskModel) {
        taskDao.updateTask(TaskEntity(taskModel.id, taskModel.task, taskModel.selected))
    }

    suspend fun  removeTask(taskModel: TaskModel){
        taskDao.deleteTask(taskModel.toData())
    }
}

fun TaskModel.toData():TaskEntity{
    return TaskEntity(this.id, this.task, this.selected)
}