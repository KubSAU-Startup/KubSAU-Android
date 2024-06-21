package com.example.diploma.network.worktypes

import com.example.diploma.common.foldOnSuccess
import com.example.diploma.model.WorkType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkTypesRepositoryImpl(private val service: WorkTypesService) : WorkTypesRepository {

    override suspend fun getWorkTypeById(workTypeId: Int): WorkType? = withContext(Dispatchers.IO) {
        service.getWorkTypeById(workTypeId).foldOnSuccess()
    }
}


