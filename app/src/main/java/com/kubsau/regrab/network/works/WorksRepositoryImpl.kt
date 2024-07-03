package com.kubsau.regrab.network.works

import com.kubsau.regrab.common.foldOnSuccess
import com.kubsau.regrab.model.EntryElement
import com.kubsau.regrab.model.Filter
import com.kubsau.regrab.model.Work
import com.kubsau.regrab.ui.screens.latestworks.model.WorkFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorksRepositoryImpl(private val service: WorksService) : WorksRepository {

    override suspend fun getLatestWorks(
        offset: Int?,
        filters: List<WorkFilter>?
    ): List<EntryElement>? = withContext(Dispatchers.IO) {
        val mapOfFilters = filters?.associate { filter -> filter.title to filter.value } ?: mapOf()
        service.getLatestWorks(offset, mapOfFilters).foldOnSuccess()?.entries
    }

    override suspend fun getWorkTypesFilters(): List<Filter>? = withContext(Dispatchers.IO) {
        service.getWorkTypesFilters().foldOnSuccess()
    }

    override suspend fun getDisciplinesFilters(): List<Filter>? = withContext(Dispatchers.IO) {
        service.getDisciplinesFilters().foldOnSuccess()
    }

    override suspend fun getEmployeesFilters(shrinkNames: Boolean): List<Filter>? =
        withContext(Dispatchers.IO) {
            service.getEmployeesFilters(shrinkNames).foldOnSuccess()
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
        val parameters = mutableMapOf(
            "disciplineId" to disciplineId.toString(),
            "studentId" to studentId.toString(),
            "workTypeId" to workTypeId.toString(),
            "employeeId" to employeeId.toString(),
        ).apply {
            title?.let { this["title"] = it }
        }

        service.registerNewWork(parameters).foldOnSuccess()
    }
}