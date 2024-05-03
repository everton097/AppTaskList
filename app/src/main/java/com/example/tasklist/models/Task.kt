package com.example.tasklist.models

data class Task(
    val titulo: String = "",
    val descricao: String = "",
    val concluido: Boolean = false,
)
