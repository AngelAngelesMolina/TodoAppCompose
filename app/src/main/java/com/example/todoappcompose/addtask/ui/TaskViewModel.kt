package com.example.todoappcompose.addtask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoappcompose.addtask.domain.AddTaskUseCase
import com.example.todoappcompose.addtask.domain.GetTasksUseCase
import com.example.todoappcompose.addtask.domain.RemoveTaskUseCase
import com.example.todoappcompose.addtask.domain.UpdateTaskUseCase
import com.example.todoappcompose.addtask.ui.TaskUiState.Success
import com.example.todoappcompose.addtask.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val removeTaskUseCase: RemoveTaskUseCase,
    getTasksUseCase: GetTasksUseCase
) : ViewModel() {
    //agarra cada uno, convierte en success, sino error, si sepone en segundo plano y pasan 5s pasalo a Loading
    val uiState: StateFlow<TaskUiState> = getTasksUseCase().map(::Success)
        .catch { TaskUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(50000), TaskUiState.Loading)

    private
    val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    /*  private val _tasks = mutableStateListOf<TaskModel>()
      val task: List<TaskModel> = _tasks*/


    fun dialogClose() {
        _showDialog.value = false
    }

    fun onTasksCreated(task: String) {
        _showDialog.value = false
//        _tasks.add(TaskModel(task = task))

        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }

    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {

        /*   val index = _tasks.indexOf(taskModel)
           _tasks[index] = _tasks[index].let {
               it.copy(selected = !it.selected)
           }*/
        viewModelScope.launch {
            updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
        }
    }

    fun onItemRemoved(taskModel: TaskModel) {
        /*     val task = _tasks.find {
                 it.id == taskModel.id
             }
             _tasks.remove(task)*/

             viewModelScope.launch {
                 removeTaskUseCase(taskModel)
             }
    }


}