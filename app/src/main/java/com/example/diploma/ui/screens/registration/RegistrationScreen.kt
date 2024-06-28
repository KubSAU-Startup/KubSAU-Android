package com.example.diploma.ui.screens.registration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diploma.R
import com.example.diploma.ui.screens.registration.camera.components.Alert
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    backToCamera: () -> Unit,
    viewModel: WorkRegisterViewModel = koinViewModel<WorkRegisterViewModelImpl>(),
) {
    val screenState by viewModel.screenState.collectAsState()

    if (screenState.showPickTeacherError) {
        Alert(
            onDismiss = viewModel::teacherErrorShown,
            title = stringResource(id = R.string.pick_teacher_error_title),
            text = stringResource(id = R.string.pick_teacher_error_text)
        )
    }

    val hideContent by remember {
        derivedStateOf { screenState.isLoading && screenState.discipline.trim().isEmpty() }
    }

    if (screenState.isNeedOpenCamera) {
        viewModel.onCameraOpened()
        backToCamera()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.title_register)) },
                navigationIcon = {
                    IconButton(onClick = backToCamera) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (hideContent) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.journal_element_discipline),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = screenState.discipline,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.journal_element_student),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = screenState.student,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (screenState.needTitle) {
                    Text(text = stringResource(id = R.string.input_work_title))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = screenState.workTitle.orEmpty(),
                        label = { Text(text = stringResource(id = R.string.hint_work_title)) },
                        onValueChange = viewModel::onWorkTitleInputChanged
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = stringResource(id = R.string.pick_teacher))
                OutlinedTextField(
                    label = { Text(text = stringResource(id = R.string.find_a_teacher)) },
                    singleLine = true,
                    value = screenState.teacherQuery,
                    onValueChange = viewModel::onTeacherQueryInputChanged,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        if (screenState.teacherQuery.isNotEmpty()) {
                            IconButton(onClick = viewModel::onTeacherClearFilterButtonClicked) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        itemsIndexed(screenState.filteredTeachers) { index, teacher ->
                            val startSpacerNeeded by remember {
                                derivedStateOf { index == 0 }
                            }

                            if (startSpacerNeeded) {
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            Row(
                                modifier = Modifier
                                    .clickable { viewModel.onTeacherClicked(teacher.id) }
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (screenState.selectedTeacherId == teacher.id),
                                    onClick = { viewModel.onTeacherClicked(teacher.id) }
                                )

                                Text(text = teacher.title)
                            }

                            val isSpacerNeeded by remember {
                                derivedStateOf {
                                    index < screenState.filteredTeachers.size - 1
                                }
                            }

                            if (isSpacerNeeded) {
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            val isEmptyItemNeeded by remember {
                                derivedStateOf { index == screenState.filteredTeachers.size - 1 }
                            }

                            if (isEmptyItemNeeded) {
                                Spacer(modifier = Modifier.height(72.dp))
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .align(Alignment.BottomCenter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (screenState.isLoading) {
                            CircularProgressIndicator()
                        } else {
                            Button(
                                onClick = viewModel::onRegisterButtonClicked,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            ) {
                                Text(text = stringResource(id = R.string.registration))
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RegPrev(modifier: Modifier = Modifier) {
    RegistrationScreen(backToCamera = {})
}
