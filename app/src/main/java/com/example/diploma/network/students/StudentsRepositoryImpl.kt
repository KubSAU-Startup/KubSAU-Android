package com.example.diploma.network.students

import com.example.diploma.common.foldOnSuccess
import com.example.diploma.model.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudentsRepositoryImpl(
    private val service: StudentsService
) : StudentsRepository {

    override suspend fun getStudentById(studentId: Int): Student? = withContext(Dispatchers.IO) {
        service.getStudentById(studentId).foldOnSuccess()
    }
}
