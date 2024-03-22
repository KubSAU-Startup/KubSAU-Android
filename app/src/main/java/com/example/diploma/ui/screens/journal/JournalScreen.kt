package com.example.diploma.ui.screens.journal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.diploma.R
import com.example.diploma.common.AppGlobal
import com.example.diploma.ui.screens.journal.components.DropMenu
import com.example.diploma.ui.screens.journal.components.JournalItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun JournalScreen() {
    val viewModel = koinViewModel<JournalVM>()

    var menuExpand by remember {
        mutableStateOf(false)
    }

    val filterRowSize = 30.dp
    val space = 8.dp
    val filterSpace = 4.dp

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(16.dp))

            Icon(painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                contentDescription = "filter icon",
                modifier = Modifier
                    .size(filterRowSize)
                    .clickable {
                        menuExpand = true
                    })

            if (menuExpand) {
                Dialog(onDismissRequest = { menuExpand = false }) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DropMenu(
                                elements = viewModel.filters.workTypes,
                                label = stringResource(id = R.string.filter_work_type)
                            ) {
                                viewModel.addFilter(
                                    filterKey = AppGlobal.Instance.getString(R.string.filter_work_type_id),
                                    element = it
                                )
                            }
                            Spacer(modifier = Modifier.height(filterSpace))
                            DropMenu(
                                elements = viewModel.filters.disciplines,
                                label = stringResource(id = R.string.filter_discipline)
                            ) {
                                viewModel.addFilter(
                                    filterKey = AppGlobal.Instance.getString(R.string.filter_discipline_id),
                                    element = it
                                )
                            }
                            Spacer(modifier = Modifier.height(filterSpace))
                            DropMenu(
                                elements = viewModel.filters.teachers,
                                label = stringResource(id = R.string.filter_teacher)
                            ) {
                                viewModel.addFilter(
                                    filterKey = AppGlobal.Instance.getString(R.string.filter_teacher_id),
                                    element = it
                                )
                            }
                            Spacer(modifier = Modifier.height(filterSpace))
                            DropMenu(
                                elements = viewModel.filters.groups,
                                label = stringResource(id = R.string.filter_group)
                            ) {
                                viewModel.addFilter(
                                    filterKey = AppGlobal.Instance.getString(R.string.filter_group_id),
                                    element = it
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextButton(
                                onClick = {
                                    menuExpand = false
                                }, modifier = Modifier.padding(8.dp)
                            ) {
                                Text(text = "Dismiss")
                            }
                            TextButton(
                                onClick = {
                                    menuExpand = false
                                }, modifier = Modifier.padding(8.dp)
                            ) {
                                Text(text = "Confirm")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(space))

            LazyRow(
                modifier = Modifier
                    .weight(1F)
                    .height(filterRowSize)
            ) {
                items(viewModel.displayedFiltersList) {
                    FilterChip(
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                        selected = true,
                        onClick = {
                            viewModel.deleteFilter(it.first)
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = it.second.title)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(space))

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search icon",
                modifier = Modifier
                    .size(filterRowSize)
                    .clickable {
                        viewModel.getJournal()
                    }
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                items(viewModel.journal) {
                    JournalItem(it)
                }

            }

        }
    }
}