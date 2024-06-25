package com.example.diploma.network.works

import com.example.diploma.model.EntryElement
import com.example.diploma.model.Filter
import com.example.diploma.model.Work
import com.example.diploma.ui.screens.latestworks.model.WorkFilter

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
