package com.example.tasklist.data

import com.example.tasklist.models.Task

fun createTaskList(): List<Task> {

    val tasks = listOf(
        Triple("Limpar casa", "Varrer e passar pano.", false),
        Triple("Estudar prova", "Prova de redes de computadores.", false),
        Triple("Lavar louça", "Louças e fogão.", false),
        Triple("Carregar celular", "Quando estiver com 20% de carga.", true)
    )

    val taskList = tasks.mapIndexed { index, (titulo, descricao, concluido) ->
        Task(titulo = titulo, descricao = descricao, concluido = concluido)
    }

    return taskList
}