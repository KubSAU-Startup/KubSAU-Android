package com.kubsau.regrab.network.students

import com.kubsau.regrab.model.Student

interface StudentsRepository {

    suspend fun getStudentById(studentId: Int): Student?
}
