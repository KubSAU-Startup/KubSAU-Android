package com.example.diploma.network.worktypes

import com.example.diploma.model.WorkType

interface WorkTypesRepository {

    suspend fun getWorkTypeById(workTypeId: Int): WorkType?
}
