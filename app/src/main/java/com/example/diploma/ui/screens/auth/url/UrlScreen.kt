package com.example.diploma.ui.screens.auth.url

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diploma.MainActivity
import com.example.diploma.R
import com.example.diploma.ui.screens.registration.camera.components.Alert
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UrlScreen(
    goToLoginScreen: () -> Unit,
    goToPreviousScreen: () -> Unit,
    viewModel: UrlViewModel = koinViewModel<UrlViewModelImpl>(),
) {
    val context = LocalContext.current
    val screenState by viewModel.screenState.collectAsState()

    if (screenState.restart) {
        Alert(
            onDismiss = viewModel::consumeRestart,
            title = stringResource(id = R.string.restart_after_url_title),
            text = stringResource(id = R.string.restart_after_url_text),
            confirmClick = { (context as? MainActivity)?.finish() },
            concealable = false
        )
    }

    if (screenState.isNeedToGoNext) {
        viewModel.onWentNext()

        if (screenState.showBackButton) {
            goToPreviousScreen()
        } else {
            goToLoginScreen()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    if (screenState.showBackButton) {
                        IconButton(
                            onClick = { goToPreviousScreen() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
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
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                )
            } else {
                Button(
                    onClick = viewModel::onNextButtonClicked,
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
    UrlScreen(
        goToLoginScreen = {},
        goToPreviousScreen = {}
    )
}
