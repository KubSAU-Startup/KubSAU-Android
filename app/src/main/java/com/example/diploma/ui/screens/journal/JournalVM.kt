package com.example.diploma.ui.screens.journal

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.network.NetworkRepo
import com.example.diploma.network.models.filter.Filter
import com.example.diploma.network.models.journal.inner.JournalElement
import kotlinx.coroutines.launch

@SuppressLint("MutableCollectionMutableState")
class JournalVM(private val repo: NetworkRepo) : ViewModel() {

    var disciplines by mutableStateOf<List<Filter>>(emptyList())
        private set

    var workTypes by mutableStateOf<List<Filter>>(emptyList())
        private set

    var employees by mutableStateOf<List<Filter>>(emptyList())

    var groups by mutableStateOf<List<Filter>>(emptyList())

    var selectedFilters by mutableStateOf<MutableMap<String, Int>>(mutableMapOf())
        private set
    var displayedFilters by mutableStateOf<MutableMap<String, Filter>>(mutableMapOf())
        private set
    var displayedFiltersList by mutableStateOf<List<Pair<String, Filter>>>(listOf())
        private set

    var journal by mutableStateOf<List<JournalElement>>(emptyList())

    init {
        viewModelScope.launch {
            disciplines = repo.getFilterDiscipline()
            workTypes = repo.getFilterWorkType()
            employees = repo.getFilterEmployee()
            groups = repo.getFilterGroup()
        }
        getJournal()
    }

    fun getJournal() {
        viewModelScope.launch {
            journal += repo.getJournal(selectedFilters).journal
        }
    }

    fun addFilter(filterKey: String, element: Filter) {
        selectedFilters += Pair(filterKey, element.id)
        displayedFilters += Pair(filterKey, element)
        displayedFiltersList = displayedFilters.toList()
    }

    fun deleteFilter(key: String) {
        selectedFilters.remove(key)
        displayedFilters.remove(key)
        displayedFiltersList = displayedFilters.toList()
    }
}