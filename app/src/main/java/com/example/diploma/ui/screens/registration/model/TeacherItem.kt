package com.example.diploma.ui.screens.registration.model

import com.example.diploma.model.Filter

data class TeacherItem(
    val id: Int,
    val title: String
)

fun Filter.toTeacher(): TeacherItem = TeacherItem(
    id = id,
    title = title
)
