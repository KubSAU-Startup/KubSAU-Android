package com.kubsau.regrab.network.worktypes

import com.kubsau.regrab.model.WorkType

interface WorkTypesRepository {

    suspend fun getWorkTypeById(workTypeId: Int): WorkType?
}
