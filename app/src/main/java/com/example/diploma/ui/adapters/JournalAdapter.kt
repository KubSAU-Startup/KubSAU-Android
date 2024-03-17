package com.example.diploma.ui.adapters

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diploma.R
import com.example.diploma.network.models.journal.inner.JournalElement
import java.util.Locale

@Composable
fun JournalAdapter(recordInJournal: JournalElement) {
    Spacer(modifier = Modifier.height(4.dp))
    Card {
        if (recordInJournal.work.title != null)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = recordInJournal.work.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 4.dp),
            ) {
                Text(
                    text = stringResource(
                        id = R.string.journal_element_discipline,
                        recordInJournal.discipline.title
                    )
                )
                Text(
                    text = stringResource(
                        id = R.string.journal_element_student,
                        recordInJournal.student.fullName
                    )
                )
                Text(
                    text = stringResource(
                        id = R.string.journal_element_student_status,
                        recordInJournal.student.status
                    )
                )
            }
            Column(
                modifier = Modifier.weight(0.5f),
            ) {
                Text(
                    text = stringResource(
                        id = R.string.journal_element_student_group,
                        recordInJournal.group.title
                    )
                )
                Text(
                    text = stringResource(
                        id = R.string.journal_element_discipline,
                        recordInJournal.discipline.title
                    )
                )
                Text(
                    text = stringResource(
                        id = R.string.journal_element_work_type,
                        recordInJournal.work.type.title
                    )
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val rawDate = recordInJournal.work.registrationDate
            val formatPattern = stringResource(id = R.string.registration_pattern)
            val sdf = SimpleDateFormat(formatPattern, Locale.getDefault())
            val displayDate = sdf.format(rawDate * ONE_SEC)

            Text(
                text = stringResource(
                    id = R.string.journal_element_registration_date,
                    displayDate
                )
            )
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

private const val ONE_SEC = 1000L