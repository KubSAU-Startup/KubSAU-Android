package com.example.diploma.ui.screens.journal.components

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.diploma.R
import com.example.diploma.network.models.journal.inner.JournalElement
import java.util.Locale

@Composable
fun JournalItem(
    record: JournalElement
) {
    val dateFormat = stringResource(id = R.string.registration_pattern)
    val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())

    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(size = 16.dp))
            .padding(8.dp)
    ) {
        Row {
            record.work.title?.let {
                Text(
                    textAlign = TextAlign.Center,
                    text = it,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        Row {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = stringResource(
                        id = R.string.journal_element_student,
                        record.student.fullName
                    ),
                )

                Text(
                    text = stringResource(
                        id = R.string.journal_element_discipline,
                        record.discipline.title
                    )
                )

                Text(
                    text = stringResource(
                        id = R.string.journal_element_student_status,
                        record.student.status.title
                    )
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(
                        id = R.string.journal_element_student_group,
                        record.group.title
                    ),
                )

                Text(
                    text = stringResource(
                        id = R.string.journal_element_teacher,
                        with(record.employee) {
                            "$lastName $firstName $middleName"
                        }
                    )
                )

                Text(
                    text = stringResource(
                        id = R.string.journal_element_work_type,
                        record.work.type.title
                    )
                )
            }
        }

        Row {
            Text(text = sdf.format(record.work.registrationDate * 1000))
        }
    }
}