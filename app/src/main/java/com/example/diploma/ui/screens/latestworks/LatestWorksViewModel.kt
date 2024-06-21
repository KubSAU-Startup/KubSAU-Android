package com.example.diploma.ui.screens.latestworks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.model.EntryElement
import com.example.diploma.model.Filter
import com.example.diploma.network.works.WorksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LatestWorksViewModel(private val repository: WorksRepository) : ViewModel() {

    var disciplines by mutableStateOf<List<Filter>>(emptyList())
        private set

    var workTypes by mutableStateOf<List<Filter>>(emptyList())
        private set

    var employees by mutableStateOf<List<Filter>>(emptyList())

    var groups by mutableStateOf<List<Filter>>(emptyList())

    // TODO: 21/06/2024, Danil Nikolaev: destroy & re-do this
//    var selectedFilters by mutableStateOf<MutableMap<String, Int>>(mutableMapOf())
//        private set
//    var displayedFilters by mutableStateOf<MutableMap<String, Filter>>(mutableMapOf())
//        private set
//    var displayedFiltersList by mutableStateOf<List<Pair<String, Filter>>>(listOf())
//        private set

    var journal by mutableStateOf<List<EntryElement>>(emptyList())

    private var offset by mutableIntStateOf(0)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            disciplines = async { repository.getDisciplinesFilters() }.await() ?: emptyList()
            workTypes = async { repository.getWorkTypesFilters() }.await() ?: emptyList()
            employees = async { repository.getEmployeesFilters() }.await() ?: emptyList()
            groups = async { repository.getGroupsFilters() }.await() ?: emptyList()
        }

        getJournal()
    }

    fun getJournal(offset: Int? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val latestWorks = repository.getLatestWorks(0, null)
            if (latestWorks == null) {
                // TODO: 21/06/2024, Danil Nikolaev: show error
            } else {
                journal = latestWorks
                this@LatestWorksViewModel.offset += latestWorks.size
            }
        }
    }

    fun addToJournal() {
        viewModelScope.launch {
            val response = repository.getLatestWorks(offset, null)
            if (response == null) {
                // TODO: 21/06/2024, Danil Nikolaev: show error
            } else {
                journal += response
                offset += response.size
            }
        }
    }

    fun addFilter(filterKey: String, element: Filter) {
//        selectedFilters += Pair(filterKey, element.id)
//        displayedFilters += Pair(filterKey, element)
//        displayedFiltersList = displayedFilters.toList()
    }

    fun deleteFilter(key: String) {
//        selectedFilters.remove(key)
//        displayedFilters.remove(key)
//        displayedFiltersList = displayedFilters.toList()
    }
}
