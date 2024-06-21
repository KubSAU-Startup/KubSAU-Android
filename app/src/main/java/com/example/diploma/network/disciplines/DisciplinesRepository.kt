package com.example.diploma.network.disciplines

import com.example.diploma.model.Discipline

interface DisciplinesRepository {

    suspend fun getDisciplineById(disciplineId: Int): Discipline?
}
