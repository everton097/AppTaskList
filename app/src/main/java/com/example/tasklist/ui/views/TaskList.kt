package com.example.tasklist.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tasklist.R
import com.example.tasklist.models.Task
import com.example.tasklist.ui.theme.TaskListTheme
import com.example.tasklist.viewmodels.TaskListViewModel

@Composable
fun TaskList(
    viewModel: TaskListViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.taskListUiState.collectAsState()

    LazyColumn {
        items(uiState.taskList) { task ->
            TaskCard(
                task = task,
                onDelete = viewModel::deletarTask,
                onEditTask = {
                    viewModel.onEditTask(
                        task = task,
                        navController = navController,
                    )
                },
                onUpdateStatus = {
                    viewModel.onUpdateStatus(
                        task = task,
                        navController = navController,
                    )
                },
                modifier = modifier
            )
        }
    }

}

@Composable
fun TaskCard(
    task: Task,
    onDelete: (Task) -> Unit,
    onUpdateStatus: (Task) -> Unit,
    onEditTask: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(2.dp)
        .clickable {
            onEditTask()
        }) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth().background(if (task.concluido) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surfaceTint)
        ) {
            Column(modifier = modifier.padding(6.dp)) {
                Text(text = task.titulo, fontWeight = if (task.concluido) FontWeight.Light else FontWeight.Bold, fontSize = 22.sp, fontStyle = if (task.concluido) FontStyle.Italic else FontStyle.Normal)
                Text(
                    text = task.descricao,
                    fontStyle = FontStyle.Italic,
                    fontWeight = if (task.concluido) FontWeight.ExtraLight else FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Black,
                    maxLines = 2,
                    modifier = modifier.width(260.dp)
                )
            }
            Spacer(modifier = modifier.weight(1f))
            if (task.concluido){
                Image(painter = painterResource(id = R.drawable.baseline_delete_outline_24),
                    contentDescription = "delete",
                    modifier
                        .padding(end = 8.dp)
                        .clickable { onDelete(task) }
                )
            }else{
                Image(painter = painterResource(id = R.drawable.baseline_check_24),
                    contentDescription = "check",
                    modifier
                        .padding(end = 8.dp)
                        .clickable { onUpdateStatus(task) }
                )
            }
        }

    }

}
@Preview(showBackground = true)
@Composable
fun ListPreview() {
    TaskListTheme {
        TaskCard(task = Task(
            titulo = "Teste",
            descricao = "Atividade a fazer de teste",
            concluido = true
        ), {}, {}, {})
    }
}