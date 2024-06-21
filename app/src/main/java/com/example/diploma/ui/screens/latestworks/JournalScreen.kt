package com.example.diploma.ui.screens.latestworks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.diploma.ui.screens.latestworks.components.JournalItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun JournalScreen(
    onError: (String) -> Unit,
    onConsumeAction: (Int) -> Unit,
    isFilterClicked: Boolean,
    isSearchClicked: Boolean,
    viewModel: LatestWorksViewModel = koinViewModel<LatestWorksViewModelImpl>()
) {

    // TODO: 21/06/2024, Danil Nikolaev: add lifecycle deps and use collectAsStateWithLifecycle
    val screenState by viewModel.screenState.collectAsState()
//    if (screenState.isUrlWrong) {
//        onError("Wrong url")
//    }

    if (isFilterClicked) {
        onConsumeAction(0)
        viewModel.onFilterButtonClicked()
    }

    if (isSearchClicked) {
        onConsumeAction(1)
        viewModel.onSearchButtonClicked()
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(screenState.entries) {
                JournalItem(it)
            }
        }
    }
}

@Preview
@Composable
fun JournalScreenPreview() {
    JournalScreen(
        onError = {},
        onConsumeAction = {},
        isFilterClicked = false,
        isSearchClicked = false,
    )
}
