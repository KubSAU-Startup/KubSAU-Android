package com.example.diploma.ui.screens.registration.model

data class WorkRegisterScreenState(
    val isLoading: Boolean,
    val discipline: String,
    val student: String,
    val needTitle: Boolean,
    val workTitle: String?,
    val teacherQuery: String,
    val teachers: List<TeacherItem>,
    val filteredTeachers: List<TeacherItem>,
    val selectedTeacherId: Int?,
    val isNeedOpenCamera: Boolean,
    val departmentId: Int,
    val workTypeId: Int,
    val studentId: Int,
    val disciplineId: Int,
    val showPickTeacherError: Boolean
) {
    companion object {

        val EMPTY: WorkRegisterScreenState = WorkRegisterScreenState(
            isLoading = false,
            discipline = "",
            student = "",
            needTitle = false,
            workTitle = null,
            teacherQuery = "",
            teachers = emptyList(),
            filteredTeachers = emptyList(),
            selectedTeacherId = null,
            isNeedOpenCamera = false,
            departmentId = -1,
            workTypeId = -1,
            studentId = -1,
            disciplineId = -1,
            showPickTeacherError = false
        )
    }
}
