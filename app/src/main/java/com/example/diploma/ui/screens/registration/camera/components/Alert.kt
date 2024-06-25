package com.example.diploma.ui.screens.registration.camera.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.diploma.R


@Composable
fun Alert(
    onDismiss: () -> Unit,
    title: String,
    text: String
) {
    AlertDialog(
        title = { Text(text = title) },
        text = { Text(text = text) },
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(
                text = stringResource(id = R.string.ok),
                modifier = Modifier.clickable {
                    onDismiss()
                }
            )
        }
    )
}
