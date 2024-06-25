package com.example.diploma.ui.screens.latestworks.components

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diploma.R
import com.example.diploma.model.EntryElement
import java.util.Locale

@Composable
fun EntryItem(
    record: EntryElement
) {
    val dateFormat = stringResource(id = R.string.registration_pattern)
    val sdf = remember { SimpleDateFormat(dateFormat, Locale.getDefault()) }

    val spacerHeight = remember { 12.dp }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(size = 16.dp))
            .padding(8.dp)
    ) {
        Row {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = record.work.type.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Start
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                            append(stringResource(id = R.string.journal_item_discipline))
                        }
                        append(": ${record.discipline.title}")
                    }
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (record.work.title != null) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                                append(stringResource(id = R.string.journal_item_title))
                            }
                            append(": ${record.work.title}")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(spacerHeight))

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                            append(stringResource(id = R.string.journal_item_student))
                        }
                        append(": ${record.student.fullName}")
                    }
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                            append(stringResource(id = R.string.journal_item_group))
                        }
                        append(": ${record.group.title}")
                    }
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                        append(stringResource(id = R.string.journal_item_teacher))
                    }
                    append(": " + with(record.employee) {
                        "$lastName $firstName $middleName"
                    })
                })
            }
        }

        Spacer(modifier = Modifier.height(spacerHeight))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = sdf.format(record.work.registrationDate),
                fontWeight = FontWeight.Bold
            )
        }
    }
}
