package com.example.tasklist.ui.views

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.tasklist.R

data class AppUiState(
    @StringRes val title: Int = R.string.task_list,
    @DrawableRes val fabIcon : Int = R.drawable.baseline_add_24,
    @StringRes val iconContentDescription: Int = R.string.insert_new_task,
)
