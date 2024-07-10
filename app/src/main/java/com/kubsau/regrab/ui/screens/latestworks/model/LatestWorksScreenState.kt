package com.kubsau.regrab.ui.screens.latestworks.model

data class LatestWorksScreenState(
    val isLoading: Boolean,
    val offset: Int,
    val entries: List<EntryModel>,
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
