package com.example.diploma.network.works

import com.example.diploma.common.foldOnSuccess
import com.example.diploma.model.EntryElement
import com.example.diploma.model.Filter
import com.example.diploma.model.Work
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorksRepositoryImpl(private val service: WorksService) : WorksRepository {

    override suspend fun getLatestWorks(
        offset: Int?,
        filters: Map<String, Int>?
    ): List<EntryElement>? = withContext(Dispatchers.IO) {
        service.getLatestWorks(offset, filters ?: mapOf()).foldOnSuccess()?.entries
    }

    override suspend fun getWorkTypesFilters(): List<Filter>? = withContext(Dispatchers.IO) {
        service.getWorkTypesFilters().foldOnSuccess()
    }

    override suspend fun getDisciplinesFilters(): List<Filter>? = withContext(Dispatchers.IO) {
        service.getDisciplinesFilters().foldOnSuccess()
    }

    override suspend fun getEmployeesFilters(): List<Filter>? = withContext(Dispatchers.IO) {
        service.getEmployeesFilters().foldOnSuccess()
    }

    override suspend fun getGroupsFilters(): List<Filter>? = withContext(Dispatchers.IO) {
        service.getGroupsFilters().foldOnSuccess()
    }

    override suspend fun registerNewWork(
        disciplineId: Int,
        studentId: Int,
        title: String?,
        workTypeId: Int,
        employeeId: Int
    ): Work? = withContext(Dispatchers.IO) {
        val parameters =
            hashMapOf<String, Any?>(
                "disciplineId" to disciplineId,
                "studentId" to studentId,
                "workTypeId" to workTypeId,
                "employeeId" to employeeId,
            ).apply {
                title?.let { this["title"] = it }
            }

        service.registerNewWork(parameters).foldOnSuccess()
    }
}
