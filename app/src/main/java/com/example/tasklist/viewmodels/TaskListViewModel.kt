package com.example.tasklist.viewmodels

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.tasklist.R
import com.example.tasklist.data.createTaskList
import com.example.tasklist.models.Task
import com.example.tasklist.ui.views.AppScreens
import com.example.tasklist.ui.views.AppUiState
import com.example.tasklist.ui.views.InsertFormUiState
import com.example.tasklist.ui.views.TaskListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskListViewModel : ViewModel() {

        private val _taskListUiState: MutableStateFlow<TaskListUiState> =
            MutableStateFlow(TaskListUiState(createTaskList()))

        val taskListUiState: StateFlow<TaskListUiState> =
            _taskListUiState.asStateFlow()
        private val _appUiState: MutableStateFlow<AppUiState> =
            MutableStateFlow(AppUiState())

        private val _insertFormUiState: MutableStateFlow<InsertFormUiState> =
            MutableStateFlow(InsertFormUiState())
        val insertFormUiState: StateFlow<InsertFormUiState> =
            _insertFormUiState.asStateFlow()

        val appUiState: StateFlow<AppUiState> =
            _appUiState.asStateFlow()

        private var taskToEdit: Task = Task()
        private var editTask: Boolean = false

        fun navigate(navController: NavController) {
            if (_appUiState.value.title == R.string.task_list) {
                _appUiState.update { currentState ->
                    currentState.copy(
                        title = R.string.insert_task,
                        fabIcon = R.drawable.baseline_check_24,
                        iconContentDescription = R.string.confirm,
                    )
                }
                navController.navigate(AppScreens.InsertTask.name)
            } else {
                val tasks: MutableList<Task> = _taskListUiState.value.taskList.toMutableList()
                if (editTask){
                    val pos = tasks.indexOf(taskToEdit)
                    tasks.removeAt(pos)
                    tasks.add(pos, Task(
                        _insertFormUiState.value.titulo,
                        _insertFormUiState.value.descricao,
                        _insertFormUiState.value.concluido,)
                    )
                    taskToEdit = Task()
                    editTask = false
                }else{
                    tasks.add(Task(
                        titulo = _insertFormUiState.value.titulo,
                        descricao = _insertFormUiState.value.descricao,
                        concluido =  _insertFormUiState.value.concluido,)
                    )
                }
                _taskListUiState.update { currentState ->
                    currentState.copy(
                        taskList = tasks.toList()
                    )
                }
                _insertFormUiState.update {
                    InsertFormUiState()
                }
                _appUiState.update {
                    AppUiState()
                }
                navController.navigate(AppScreens.TaskList.name){
                    popUpTo(AppScreens.TaskList.name){
                        inclusive = true
                    }
                }
            }
        }

        fun navigateBack(navController: NavController){
            _appUiState.update {
                AppUiState()
            }
            navController.popBackStack()
        }

        fun deletarTask(task: Task) {
            val tasks: MutableList<Task> = _taskListUiState.value.taskList.toMutableList()
            tasks.remove(task)
            _taskListUiState.value = _taskListUiState.value.copy(
                taskList = tasks.toList()
            )
        }

        fun onEditTask(task: Task, navController: NavController){
            editTask= true
            taskToEdit = task
            _insertFormUiState.update { currentState ->
                currentState.copy(
                    titulo = taskToEdit.titulo,
                    descricao = taskToEdit.descricao,
                    concluido = taskToEdit.concluido,
                )
            }
            _appUiState.update { currentState ->
                currentState.copy(
                    title = R.string.edit_task,
                    fabIcon = R.drawable.baseline_check_24,
                    iconContentDescription = R.string.confirm,
                )
            }
            navController.navigate(route = AppScreens.InsertTask.name)
        }

        fun onNameChage(titulo:String){
            _insertFormUiState.update { currentState ->
                currentState.copy(
                    titulo = titulo
                )
            }
        }
        fun onUpdateDescricao(descricao:String){
            _insertFormUiState.update { currentState ->
                currentState.copy(
                    descricao = descricao
                )
            }
        }
        fun onUpdateStatus(task: Task, navController: NavController){
            val tasks: MutableList<Task> = _taskListUiState.value.taskList.toMutableList()
            val pos = tasks.indexOf(task)
            if (pos != -1) {
                val updatedTask = task.copy(concluido = !task.concluido)
                tasks[pos] = updatedTask
            }
            _taskListUiState.update { currentState ->
                currentState.copy(
                    taskList = tasks.toList()
                )
            }
        }
    }