package com.example.diploma.ui.screens.journal

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.NetworkRepo
import com.example.diploma.network.models.filter.Filter
import com.example.diploma.network.models.filter.FilterElement
import kotlinx.coroutines.launch

@SuppressLint("MutableCollectionMutableState")
class JournalVM(private val repo: NetworkRepo) : ViewModel() {
    var filters by mutableStateOf(Filter())
        private set

    var workTypeFilters by mutableStateOf<List<FilterElement>>(listOf())
        private set
    var disciplineFilters by mutableStateOf<List<FilterElement>>(listOf())
        private set
    var teacherFilters by mutableStateOf<List<FilterElement>>(listOf())
        private set
    var groupFilters by mutableStateOf<List<FilterElement>>(listOf())
        private set
    var departmentFilters by mutableStateOf<List<FilterElement>>(listOf())
        private set

    var selectedFilters by mutableStateOf<MutableMap<String, Int>>(mutableMapOf())
        private set
    var displayedFilters by mutableStateOf<List<Pair<String, FilterElement>>>(listOf())
        private set

    init {
        viewModelScope.launch {
            filters = repo.getFilters()
            workTypeFilters = filters.workTypes
            disciplineFilters = filters.disciplines
            teacherFilters = filters.teachers
            groupFilters = filters.groups
            departmentFilters = filters.departments

            selectedFilters += Pair("Test1", 0)
            selectedFilters += Pair("Test2", 1)
            selectedFilters += Pair("Test3", 2)
            selectedFilters += Pair("Test4", 3)
        }
    }

    fun addFilter(key: String, element: FilterElement) {
        selectedFilters += Pair(key, element.id)
        displayedFilters += Pair(key, element)
    }

    fun deleteFilter(key: String) {
        selectedFilters.remove(key = key)
    }
}