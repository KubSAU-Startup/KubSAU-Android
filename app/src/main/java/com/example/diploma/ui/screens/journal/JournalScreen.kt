package com.example.diploma.ui.screens.journal

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.diploma.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen() {
    val viewModel = koinViewModel<JournalVM>()

    var showFilters by remember {
        mutableStateOf(value = false)
    }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val scrollState = rememberScrollState()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(text = "Show filters")
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                        contentDescription = ""
                    )
                },
                onClick = { showFilters = true })
        }
    ) { paddings ->
        if (showFilters) {
            ModalBottomSheet(
                modifier = Modifier.padding(paddings),
                onDismissRequest = {
                    showFilters = false
                },
                sheetState = sheetState
            ) {
                LazyRow(
                    modifier = Modifier
                        .scrollable(
                            orientation = Orientation.Horizontal,
                            state = scrollState
                        )
                        .fillMaxWidth()
                ) {
                    items(viewModel.displayedFilters) {
                        InputChip(
                            selected = true,
                            onClick = {
                                viewModel.deleteFilter(key = it.first)
                            },
                            label = { Text(text = it.second.title) },
                            trailingIcon = {
                                Icon(Icons.Default.Clear, contentDescription = null)
                            }
                        )
                    }
                }
            }
        }
    }
}