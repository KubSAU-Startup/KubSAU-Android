package com.example.diploma.ui.screens.journal.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.diploma.network.models.filter.FilterElement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropMenu(
    elements: List<FilterElement>,
    label: String,
    addFilters: (FilterElement) -> Unit
) {
    var isExpended by remember {
        mutableStateOf(false)
    }

    var selected by remember {
        mutableStateOf("")
    }

    val interactionSource = remember { MutableInteractionSource() }

    ExposedDropdownMenuBox(
        expanded = isExpended,
        onExpandedChange = { change ->
            isExpended = change
        }
    ) {
        OutlinedTextField(
            label = {
                Text(text = label)
            },
            value = selected,
            onValueChange = {
                selected = it
            },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpended)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = isExpended,
            onDismissRequest = { isExpended = false },
        ) {
            elements.forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it.title)
                    },
                    onClick = {
                        selected = it.title
                        addFilters(it)
                        isExpended = false
                    },
                    interactionSource = interactionSource.apply {

                    }
                )
            }
        }
    }
}