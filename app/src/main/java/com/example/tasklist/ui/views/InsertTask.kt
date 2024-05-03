package com.example.tasklist.ui.views

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tasklist.viewmodels.TaskListViewModel

@Composable
fun InsertTask(
    viewModel: TaskListViewModel,
    navController: NavController,
) {
    BackHandler {
        viewModel.navigateBack(navController)
    }
    val uiState by viewModel.insertFormUiState.collectAsState()
    InsertForm(
        titulo = uiState.titulo,
        descricao = uiState.descricao,
        onUpdateName = viewModel::onNameChage,
        onUpdateDescricao = viewModel::onUpdateDescricao,
    )
}

@Composable
fun InsertForm(
    titulo: String,
    descricao: String,
    onUpdateName: (String) -> Unit,
    onUpdateDescricao: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        TextField(value = titulo,
            onValueChange = onUpdateName,
            label = { Text(text = "Titulo") },
            singleLine = true,
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(8.dp))
        TextField(value = descricao,
            onValueChange = onUpdateDescricao,
            label = { Text(text = "Descricao") },
            singleLine = false,
            minLines = 1,
            maxLines = 3,
            modifier = modifier.fillMaxWidth()
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun InsertFormPreview() {
    InsertForm( "", "", {}, {})
}