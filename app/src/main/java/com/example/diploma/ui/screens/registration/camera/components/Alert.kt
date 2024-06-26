package com.example.diploma.ui.screens.registration.camera.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.example.diploma.R


@Composable
fun Alert(
    onDismiss: () -> Unit,
    title: String,
    text: String,
    confirmClick: (() -> Unit)? = null,
    concealable: Boolean = true
) {
    AlertDialog(
        title = { Text(text = title) },
        text = { Text(text = text) },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = confirmClick ?: onDismiss) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        properties = DialogProperties(
            dismissOnClickOutside = concealable,
            dismissOnBackPress = concealable
        )
    )
}
