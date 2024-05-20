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
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    viewModel: RegistrationVM = koinViewModel(),
    qrResult: String?, goToCamera: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        if (qrResult == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { goToCamera() }) {
                    Text(text = stringResource(id = R.string.scan_qr))
                }
            }
        } else {
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
                            value = viewModel.workTitle ?: EMPTY_STRING,
                            label = {
                                Text(text = stringResource(id = R.string.hint_work_title))
                            },
                            onValueChange = {
                                viewModel.workTitle = it
                            })
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                ) {
                    items(viewModel.employeeList) { employee ->
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
                    Button(onClick = { viewModel.registration() }) {
                        Text(text = stringResource(id = R.string.registration))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RegPrev(modifier: Modifier = Modifier) {
//    departmentId, disciplineId, studentId, workTypeId
    RegistrationScreen(qrResult = "3,1,1,2") {}
}