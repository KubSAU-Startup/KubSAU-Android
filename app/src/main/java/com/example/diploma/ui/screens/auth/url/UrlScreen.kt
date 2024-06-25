package com.example.diploma.ui.screens.auth.url

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diploma.R
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

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.title_input_url),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(.9f),
                    value = screenState.url,
                    onValueChange = viewModel::onTextInputChanged,
                    label = {
                        Text(text = "https://domain.com")
                    },
                    supportingText = {
                        val errorTextResId: Int? = when {
                            screenState.isUrlWrong -> R.string.wrong_url
                            screenState.isUrlFormatInvalid -> R.string.wrong_url_format
                            else -> null
                        }

                        errorTextResId?.let {
                            Text(
                                text = stringResource(id = errorTextResId),
                                color = Color.Red
                            )
                        }
                    }
                )
            }

            if (screenState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.BottomCenter))
            } else {
                Button(
                    onClick = viewModel::onNextButtonClick,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(.9f)
                        .padding(bottom = 12.dp)
                        .height(56.dp)
                ) {
                    Text(text = stringResource(id = R.string.action_next))
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
