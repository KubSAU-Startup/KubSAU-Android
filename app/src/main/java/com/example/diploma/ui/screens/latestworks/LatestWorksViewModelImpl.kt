package com.example.diploma.ui.screens.latestworks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.R
import com.example.diploma.network.works.WorksRepository
import com.example.diploma.ui.screens.latestworks.model.FilterItem
import com.example.diploma.ui.screens.latestworks.model.LatestWorksScreenState
import com.example.diploma.ui.screens.latestworks.model.WorkFilter
import com.example.diploma.ui.screens.latestworks.model.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

interface LatestWorksViewModel {

    val screenState: StateFlow<LatestWorksScreenState>

    fun onSearchButtonClicked()
    fun onNeedToLoadMoreItems()

    fun onBackPressed()

    fun onFilterClicked(blockIndex: Int, filterIndex: Int)
    fun onClearFiltersButtonClicked()
    fun onApplyFiltersButtonClicked()
    fun onQueryChanged(newQuery: String)
}

class LatestWorksViewModelImpl(private val repository: WorksRepository) : ViewModel(),
    LatestWorksViewModel {

    override val screenState = MutableStateFlow(LatestWorksScreenState.EMPTY)

    private val dateFormatter = SimpleDateFormat(DATE_FORMAT)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val disciplines = async { repository.getDisciplinesFilters() }.await() ?: emptyList()
            val workTypes = async { repository.getWorkTypesFilters() }.await() ?: emptyList()
            val employees = async { repository.getEmployeesFilters(true) }.await() ?: emptyList()
            val groups = async { repository.getGroupsFilters() }.await() ?: emptyList()

            screenState.emit(
                screenState.value.copy(
                    filterItems = listOf(
                        FilterItem(
                            iconResId = R.drawable.round_description_24,
                            titleResId = R.string.filter_block_work_types,
                            filters = workTypes,
                            selectedFilterIndex = -1,
                            filterGroup = "workType"
                        ),
                        FilterItem(
                            iconResId = R.drawable.round_face_4_24,
                            titleResId = R.string.filter_block_teachers,
                            filters = employees,
                            selectedFilterIndex = -1,
                            filterGroup = "employee"
                        ),
                        FilterItem(
                            iconResId = R.drawable.round_group_24,
                            titleResId = R.string.filter_block_groups,
                            filters = groups,
                            selectedFilterIndex = -1,
                            filterGroup = "group"
                        ),
                        FilterItem(
                            iconResId = R.drawable.round_school_24,
                            titleResId = R.string.filter_block_disciplines,
                            filters = disciplines,
                            selectedFilterIndex = -1,
                            filterGroup = "discipline"
                        )
                    )
                )
            )
        }

        loadLatestWorks(0)
    }

    private fun loadLatestWorks(offset: Int, filters: List<WorkFilter>? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                screenState.emit(screenState.value.copy(isLoading = true))
            }

            val latestWorks = repository.getLatestWorks(offset, filters)
            if (latestWorks == null) {
                // TODO: 21/06/2024, Danil Nikolaev: show error
            } else {
                val newEntries = screenState.value.entries.toMutableList()
                if (offset == 0) {
                    newEntries.clear()
                }

                newEntries += latestWorks.map { entry -> entry.toModel(dateFormatter) }
                screenState.emit(
                    screenState.value.copy(
                        entries = newEntries,
                        offset = offset
                    )
                )
            }

            withContext(Dispatchers.Main) {
                screenState.emit(screenState.value.copy(isLoading = false))
            }
        }
    }

    override fun onNeedToLoadMoreItems() {
        loadLatestWorks(screenState.value.entries.size)
    }

    override fun onBackPressed() {
        if (screenState.value.isFiltersVisible) {
            viewModelScope.launch(Dispatchers.Main) {
                screenState.emit(screenState.value.copy(isFiltersVisible = false))
            }
        }
    }

    override fun onFilterClicked(blockIndex: Int, filterIndex: Int) {
        val newFilterItems = screenState.value.filterItems.toMutableList()
        newFilterItems[blockIndex] = newFilterItems[blockIndex].copy(
            selectedFilterIndex = filterIndex
        )

        viewModelScope.launch(Dispatchers.Main) {
            screenState.emit(screenState.value.copy(filterItems = newFilterItems))
        }
    }

    override fun onClearFiltersButtonClicked() {
        val newFilterItems = screenState.value.filterItems.toMutableList()
        newFilterItems.forEachIndexed { index, item ->
            newFilterItems[index] = item.copy(selectedFilterIndex = -1)
        }

        viewModelScope.launch(Dispatchers.Main) {
            screenState.emit(
                screenState.value.copy(
                    filterItems = newFilterItems,
                    query = ""
                )
            )
        }
    }

    override fun onApplyFiltersButtonClicked() {
        val newFilters = screenState.value.filterItems.filter(FilterItem::hasSelectedFilter)
            .map { block ->
                WorkFilter(
                    title = "${block.filterGroup}Id",
                    value = block.filters[block.selectedFilterIndex].id.toString()
                )
            }.toMutableList()

        val query = screenState.value.query.trim()
        if (query.isNotEmpty()) {
            newFilters += WorkFilter(
                title = "query",
                value = query
            )
        }

        viewModelScope.launch(Dispatchers.Main) {
            screenState.emit(
                screenState.value.copy(
                    isFiltersVisible = false,
                    appliedFilters = newFilters,
                    query = query.trim()
                )
            )
        }

        loadLatestWorks(0, newFilters)
    }

    override fun onQueryChanged(newQuery: String) {
        val newValue = screenState.value.copy(
            query = newQuery
        )

        screenState.update { newValue }
    }

    override fun onSearchButtonClicked() {
        if (screenState.value.isLoading) return

        viewModelScope.launch(Dispatchers.Main) {
            screenState.emit(
                screenState.value.copy(
                    isFiltersVisible = !screenState.value.isFiltersVisible
                )
            )
        }
    }

    companion object {
        const val DATE_FORMAT = "dd.MM.yyyy"
    }
}
