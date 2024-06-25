package com.example.diploma.ui.screens.latestworks.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diploma.R
import com.example.diploma.ui.screens.latestworks.model.EntryModel

private const val SPACER_HEIGHT = 8

@Composable
fun EntryItem(entry: EntryModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height((SPACER_HEIGHT * 2).dp))

            Text(
                text = entry.workType,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height((SPACER_HEIGHT * 2).dp))


            HorizontalDivider()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.journal_item_discipline),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = entry.discipline,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(SPACER_HEIGHT.dp))

                entry.title?.let { title ->
                    Text(
                        text = stringResource(id = R.string.journal_item_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(SPACER_HEIGHT.dp))
                }

                Text(
                    text = stringResource(id = R.string.journal_item_student),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = entry.student,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(SPACER_HEIGHT.dp))

                Text(
                    text = stringResource(id = R.string.journal_item_group),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = entry.group,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(SPACER_HEIGHT.dp))

                Text(
                    text = stringResource(id = R.string.journal_item_teacher),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = entry.teacher,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            HorizontalDivider()
            Spacer(modifier = Modifier.height(SPACER_HEIGHT.dp))

            Text(
                text = entry.date,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.height(SPACER_HEIGHT.dp))
        }
    }
}
