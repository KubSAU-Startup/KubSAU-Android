package com.example.diploma.ui.screens.auth.url

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.diploma.common.storage.NetworkConfig
import org.koin.androidx.compose.koinViewModel

@Composable
fun UrlScreen(
    viewModel: UrlVM = koinViewModel(),
    goToLoginScreen: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Log.d("URLScreen", "Render")

            Text(text = "Input server url")

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(.75f),
                value = viewModel.url,
                onValueChange = {
                    viewModel.updateUrl(it)
                },
                supportingText = {
                    if (viewModel.showUrlError)
                        Text(
                            text = "Not a url",
                            color = Color.Red
                        )
                }
            )

            Button(onClick = {
                if (viewModel.legitUrl()) {
                    goToLoginScreen()
                }
            }) {
                Text(text = "Next")
            }
        }
    }
}

@Preview
@Composable
fun UrlScreenPreview() {
    UrlScreen {}
}