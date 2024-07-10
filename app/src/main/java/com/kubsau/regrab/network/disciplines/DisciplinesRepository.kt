package com.kubsau.regrab.network.disciplines

import com.kubsau.regrab.model.Discipline

interface DisciplinesRepository {

    suspend fun getDisciplineById(disciplineId: Int): Discipline?
}
