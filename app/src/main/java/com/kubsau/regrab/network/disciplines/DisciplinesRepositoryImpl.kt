package com.kubsau.regrab.network.disciplines

import com.kubsau.regrab.common.foldOnSuccess
import com.kubsau.regrab.model.Discipline
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
