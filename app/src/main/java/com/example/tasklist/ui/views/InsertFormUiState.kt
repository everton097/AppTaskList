package com.example.tasklist.ui.views

import androidx.annotation.DrawableRes

data class InsertFormUiState(
    val titulo: String = "",
    val descricao: String = "",
    val concluido: Boolean = false,
)
