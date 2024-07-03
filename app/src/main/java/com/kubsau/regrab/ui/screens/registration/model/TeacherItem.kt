package com.kubsau.regrab.ui.screens.registration.model

import com.kubsau.regrab.model.Filter

data class TeacherItem(
    val id: Int,
    val title: String
)

fun Filter.toTeacher(): TeacherItem = TeacherItem(
    id = id,
    title = title
)
