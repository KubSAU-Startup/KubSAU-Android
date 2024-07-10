package com.kubsau.regrab.network.worktypes

import com.kubsau.regrab.common.foldOnSuccess
import com.kubsau.regrab.model.WorkType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkTypesRepositoryImpl(private val service: WorkTypesService) : WorkTypesRepository {

    override suspend fun getWorkTypeById(workTypeId: Int): WorkType? = withContext(Dispatchers.IO) {
        service.getWorkTypeById(workTypeId).foldOnSuccess()
    }
}


