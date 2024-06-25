package com.example.diploma.ui.screens.latestworks.model

import com.example.diploma.model.EntryElement

data class LatestWorksScreenState(
    val isLoading: Boolean,
    val offset: Int,
    val entries: List<EntryElement>,
    val isFiltersVisible: Boolean,
    val filterItems: List<FilterItem>,
    val appliedFilters: List<WorkFilter>,
    val query: String
) {

    companion object {
        val EMPTY: LatestWorksScreenState = LatestWorksScreenState(
            isLoading = true,
            offset = 0,
            entries = emptyList(),
            isFiltersVisible = false,
            filterItems = emptyList(),
            appliedFilters = emptyList(),
            query = ""
        )
    }
}
