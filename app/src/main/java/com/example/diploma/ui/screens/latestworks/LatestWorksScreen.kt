package com.example.diploma.ui.screens.latestworks

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diploma.R
import com.example.diploma.model.Filter
import com.example.diploma.ui.screens.latestworks.components.EntryItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LatestWorksScreen(
    onError: (String) -> Unit,
    viewModel: LatestWorksViewModel = koinViewModel<LatestWorksViewModelImpl>()
) {
    // TODO: 21/06/2024, Danil Nikolaev: add lifecycle deps and use collectAsStateWithLifecycle
    val screenState by viewModel.screenState.collectAsState()

    BackHandler(enabled = screenState.isFiltersVisible) {
        viewModel.onBackPressed()
    }

    val listState = rememberLazyListState()

    val isEntriesSizeSufficient by remember(screenState) {
        derivedStateOf {
            screenState.entries.size >= 30
        }
    }

    val togglePaging by remember(screenState) {
        derivedStateOf {
            isEntriesSizeSufficient && screenState.entries.size - listState.firstVisibleItemIndex <= 10
        }
    }

    if (togglePaging) {
        viewModel.onNeedToLoadMoreItems()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.nav_home))
                    },
                    actions = {
                        IconButton(
                            onClick = viewModel::onRefreshButtonClicked
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Refresh,
                                contentDescription = null
                            )
                        }
                        IconButton(
                            onClick = viewModel::onSearchButtonClicked
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = null
                            )
                        }
                    }
                )
            },
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                when {
                    screenState.isLoading && screenState.entries.isEmpty() -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    else -> {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(screenState.entries) { item ->
                                EntryItem(entry = item)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = screenState.isFiltersVisible,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .imePadding()
                .navigationBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.title_filters)) },
                    navigationIcon = {
                        IconButton(onClick = viewModel::onBackPressed) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = viewModel::onClearFiltersButtonClicked
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = null
                            )
                        }

                        IconButton(
                            onClick = viewModel::onApplyFiltersButtonClicked
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Done,
                                contentDescription = null
                            )
                        }
                    }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = screenState.query,
                    onValueChange = { newText ->
                        viewModel.onQueryChanged(newText)
                    },
                    label = { Text(text = stringResource(id = R.string.filter_query)) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    itemsIndexed(screenState.filterItems) { blockIndex, item ->
                        FilterBlock(
                            iconResId = item.iconResId,
                            titleResId = item.titleResId,
                            filters = item.filters,
                            selectedFilterIndex = item.selectedFilterIndex,
                            onFilterClicked = { index ->
                                viewModel.onFilterClicked(
                                    blockIndex,
                                    index
                                )
                            }
                        )

                        val isSpacerNeeded by remember {
                            derivedStateOf { blockIndex < screenState.filterItems.size - 1 }
                        }

                        if (isSpacerNeeded) {
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterBlock(
    iconResId: Int,
    titleResId: Int,
    filters: List<Filter>,
    selectedFilterIndex: Int,
    onFilterClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Icon(
            painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = titleResId),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEachIndexed { index, filter ->
            FilterChip(
                selected = selectedFilterIndex == index,
                onClick = { onFilterClicked(index) },
                label = filter.title
            )
        }
    }
}

@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text = label) },
        modifier = modifier
    )
}

@Preview
@Composable
fun JournalScreenPreview() {
    LatestWorksScreen(onError = {})
}
