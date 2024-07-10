package com.kubsau.regrab.network.works

import com.kubsau.regrab.model.EntryElement
import com.kubsau.regrab.model.Filter
import com.kubsau.regrab.model.Work
import com.kubsau.regrab.ui.screens.latestworks.model.WorkFilter

interface WorksRepository {

    suspend fun getLatestWorks(offset: Int?, filters: List<WorkFilter>?): List<EntryElement>?
    suspend fun getWorkTypesFilters(): List<Filter>?
    suspend fun getDisciplinesFilters(): List<Filter>?
    suspend fun getEmployeesFilters(shrinkNames: Boolean): List<Filter>?
    suspend fun getGroupsFilters(): List<Filter>?

    suspend fun registerNewWork(
        disciplineId: Int,
        studentId: Int,
        title: String?,
        workTypeId: Int,
        employeeId: Int
    ): Work?
}
