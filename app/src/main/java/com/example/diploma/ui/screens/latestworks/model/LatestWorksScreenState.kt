package com.example.diploma.ui.screens.latestworks.model

import com.example.diploma.model.EntryElement
import com.example.diploma.model.Filter

data class LatestWorksScreenState(
    val offset: Int,
    val entries: List<EntryElement>,
    val workTypes: List<Filter>,
    val filterWorkTypes: List<Filter>,
    val disciplines: List<Filter>,
    val filterDisciplines: List<Filter>,
    val employees: List<Filter>,
    val filterEmployees: List<Filter>,
    val groups: List<Filter>,
    val filterGroups: List<Filter>,
    val isFiltersMenuExpanded: Boolean
) {

    companion object {
        val EMPTY: LatestWorksScreenState = LatestWorksScreenState(
            offset = 0,
            entries = emptyList(),
            workTypes = emptyList(),
            filterWorkTypes = emptyList(),
            disciplines = emptyList(),
            filterDisciplines = emptyList(),
            employees = emptyList(),
            filterEmployees = emptyList(),
            groups = emptyList(),
            filterGroups = emptyList(),
            isFiltersMenuExpanded = false
        )
    }
}
