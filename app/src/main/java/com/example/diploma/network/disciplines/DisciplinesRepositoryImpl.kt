package com.example.diploma.network.disciplines

import com.example.diploma.common.foldOnSuccess
import com.example.diploma.model.Discipline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DisciplinesRepositoryImpl(
    private val service: DisciplinesService
) : DisciplinesRepository {

    override suspend fun getDisciplineById(disciplineId: Int): Discipline? =
        withContext(Dispatchers.IO) {
            service.getDisciplineById(disciplineId).foldOnSuccess()
        }
}
