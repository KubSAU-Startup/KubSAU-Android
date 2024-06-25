package com.example.diploma.ui.screens.latestworks.model

import com.example.diploma.model.Filter

data class FilterItem(
    val iconResId: Int,
    val titleResId: Int,
    val filters: List<Filter>,
    val selectedFilterIndex: Int,
    val filterGroup: String
) {
    fun hasSelectedFilter(): Boolean = selectedFilterIndex != -1
}
