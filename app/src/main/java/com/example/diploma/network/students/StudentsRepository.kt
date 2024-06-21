package com.example.diploma.network.students

import com.example.diploma.model.Student

interface StudentsRepository {

    suspend fun getStudentById(studentId: Int): Student?
}
