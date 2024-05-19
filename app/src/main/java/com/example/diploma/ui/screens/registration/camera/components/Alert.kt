package com.example.diploma.ui.screens.registration.camera.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun Alert(
    onDismiss: () -> Unit,
    title: String,
    text: String
) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            Text(text = "Ok", modifier = Modifier.clickable {
                onDismiss()
            })
        }
    )
}