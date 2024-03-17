package com.example.diploma.ui.screens.registration

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RegistrationScreen(qrResult: String?, goToCamera: () -> Unit) {
    Text(text = qrResult ?: "Go to camera",
        modifier = Modifier.clickable {
            goToCamera()
        })
}