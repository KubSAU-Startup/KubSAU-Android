package com.example.diploma.ui.screens.auth.url

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel

@Composable
fun UrlScreen(
    viewModel: UrlViewModel = koinViewModel(),
    goToLoginScreen: () -> Unit
) {
    val screenState = viewModel.screenState
    if (screenState.isNeedToGoNext) {
        viewModel.onWentNext()
        goToLoginScreen()
    }

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
                value = screenState.url,
                onValueChange = viewModel::onTextInputChanged,
                supportingText = {
                    val errorText: String? = when {
                        screenState.isUrlWrong -> "Wrong url"
                        screenState.isUrlFormatInvalid -> "Not an url"
                        else -> null
                    }

                    errorText?.let {
                        Text(
                            text = errorText,
                            color = Color.Red
                        )
                    }
                }
            )

            if (screenState.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(onClick = viewModel::onNextButtonClick) {
                    Text(text = "Next")
                }
            }
        }
    }
}

@Preview
@Composable
fun UrlScreenPreview() {
    UrlScreen {}
}
