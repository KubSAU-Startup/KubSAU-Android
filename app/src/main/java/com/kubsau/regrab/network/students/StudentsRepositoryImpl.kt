package com.kubsau.regrab.network.students

import com.kubsau.regrab.common.foldOnSuccess
import com.kubsau.regrab.model.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudentsRepositoryImpl(
    private val service: StudentsService
) : StudentsRepository {

    override suspend fun getStudentById(studentId: Int): Student? = withContext(Dispatchers.IO) {
        service.getStudentById(studentId).foldOnSuccess()
    }
}
