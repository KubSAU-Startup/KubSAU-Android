package com.example.diploma.ui.screens.latestworks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.model.Filter
import com.example.diploma.network.works.WorksRepository
import com.example.diploma.ui.screens.latestworks.model.LatestWorksScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface LatestWorksViewModel {

    val screenState: StateFlow<LatestWorksScreenState>

    fun onFilterButtonClicked()
    fun onFilterAlertConfirmClicked()
    fun onFilterAlertDismissed()

    fun onSearchButtonClicked()
    fun onNeedToLoadMoreItems()
}

class LatestWorksViewModelImpl(private val repository: WorksRepository) : ViewModel(),
    LatestWorksViewModel {

    override val screenState = MutableStateFlow(LatestWorksScreenState.EMPTY)

    // TODO: 21/06/2024, Danil Nikolaev: destroy & re-do this
//    var selectedFilters by mutableStateOf<MutableMap<String, Int>>(mutableMapOf())
//        private set
//    var displayedFilters by mutableStateOf<MutableMap<String, Filter>>(mutableMapOf())
//        private set
//    var displayedFiltersList by mutableStateOf<List<Pair<String, Filter>>>(listOf())
//        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val disciplines = async { repository.getDisciplinesFilters() }.await() ?: emptyList()
            val workTypes = async { repository.getWorkTypesFilters() }.await() ?: emptyList()
            val employees = async { repository.getEmployeesFilters() }.await() ?: emptyList()
            val groups = async { repository.getGroupsFilters() }.await() ?: emptyList()

            screenState.emit(
                screenState.value.copy(
                    disciplines = disciplines,
                    workTypes = workTypes,
                    employees = employees,
                    groups = groups
                )
            )
        }

        loadLatestWorks(0)
    }

    private fun loadLatestWorks(offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val latestWorks = repository.getLatestWorks(offset, null)
            if (latestWorks == null) {
                // TODO: 21/06/2024, Danil Nikolaev: show error
            } else {
                val newEntries = screenState.value.entries.toMutableList()
                if (offset == 0) {
                    newEntries.clear()
                }

                newEntries += latestWorks
                screenState.emit(
                    screenState.value.copy(
                        entries = newEntries,
                        offset = offset
                    )
                )
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

    override fun onNeedToLoadMoreItems() {
        loadLatestWorks(screenState.value.entries.size)
    }

    override fun onFilterButtonClicked() {
        viewModelScope.launch(Dispatchers.Main) {
            screenState.emit(
                screenState.value.copy(
                    isFiltersMenuExpanded = !screenState.value.isFiltersMenuExpanded
                )
            )
        }
    }

    override fun onFilterAlertConfirmClicked() {
        loadLatestWorks(0)
    }

    override fun onFilterAlertDismissed() {
        viewModelScope.launch(Dispatchers.Main) {
            screenState.emit(
                screenState.value.copy(
                    isFiltersMenuExpanded = false
                )
            )
        }
    }

    override fun onSearchButtonClicked() {
        loadLatestWorks(0)
    }
}
