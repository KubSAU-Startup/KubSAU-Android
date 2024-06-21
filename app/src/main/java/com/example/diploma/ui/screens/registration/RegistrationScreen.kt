package com.example.diploma.ui.screens.registration

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diploma.R
import com.example.diploma.common.EMPTY_STRING
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    viewModel: WorkRegistrationViewModel = koinViewModel(),
    qrResult: String, backToCamera: () -> Unit
) {
    if (viewModel.returnToCamera)
        backToCamera()

    val searchIcon = if (viewModel.teacherSearch.isEmpty()) {
        Icons.Default.Search
    } else {
        Icons.Default.Close
    }

    var showSearch by remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        viewModel.fetchData(data = qrResult)

        Log.e("Reg_screen_render", "Render occur")

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(
                        id = R.string.journal_element_discipline,
                        viewModel.discipline
                    )
                )

                Text(
                    text = stringResource(
                        id = R.string.journal_element_student,
                        viewModel.student
                    )
                )

                if (viewModel.needTitle) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(.90f),
                        value = viewModel.workTitle ?: EMPTY_STRING,
                        label = {
                            Text(text = stringResource(id = R.string.hint_work_title))
                        },
                        onValueChange = {
                            viewModel.workTitle = it
                        })
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(id = R.string.teacher_id_error)
                )

                Spacer(modifier = Modifier.weight(1f))

                if (!showSearch)
                    Row(
                        modifier = Modifier.fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            imageVector = searchIcon,
                            contentDescription = "Search icon for teachers",
                            modifier = Modifier.clickable {
                                showSearch = true
                            }
                        )
                    }

            }

            HorizontalDivider(
                thickness = 4.dp
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (showSearch) {
                OutlinedTextField(
                    placeholder = {
                        Text(text = stringResource(id = R.string.find_a_teacher))
                    },
                    singleLine = true,
                    value = viewModel.teacherSearch,
                    onValueChange = {
                        viewModel.filterTeachers(it)
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Search icon for teachers",
                            modifier = Modifier.clickable {
                                if (viewModel.teacherSearch.isNotEmpty()) {
                                    viewModel.clearTeacherSearch()
                                } else {
                                    showSearch = false
                                }
                            }
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                items(viewModel.employeeListDisplayed) { employee ->
                    Row(
                        modifier = Modifier
                            .clickable {
                                viewModel.employeeId = employee.id
                                viewModel.selectedEmployee = employee.title
                            }
                            .border(
                                width = 2.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (viewModel.selectedEmployee == employee.title),
                            onClick = {}
                        )

                        Text(text = employee.title)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Row {
                Button(
                    onClick = { viewModel.registration() },
                    enabled = viewModel.regButEnable
                ) {
                    Text(text = stringResource(id = R.string.registration))
                }
            }
        }
    }
}

@Preview
@Composable
fun RegPrev(modifier: Modifier = Modifier) {
//    departmentId, disciplineId, studentId, workTypeId
    RegistrationScreen(qrResult = "3,1,1,1") {}
}
