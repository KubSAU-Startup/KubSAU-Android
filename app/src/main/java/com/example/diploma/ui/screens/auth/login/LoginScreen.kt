package com.example.diploma.ui.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.diploma.R
import com.example.diploma.network.ErrorDomain
import com.example.diploma.ui.screens.registration.camera.components.Alert
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onError: (ErrorDomain) -> Unit,
    moveToMainRoot: () -> Unit,
    openUrlScreen: () -> Unit,
    viewModel: LoginViewModelImpl = koinViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val apiError by viewModel.errorDomain.collectAsStateWithLifecycle()
    apiError?.let { error ->
        viewModel.apiErrorConsumed()
        onError(error)
    }

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    if (screenState.showWrongCredentialsError) {
        Alert(
            onDismiss = viewModel::wrongCredentialsErrorShown,
            title = stringResource(id = R.string.wrong_credentials_title),
            text = stringResource(id = R.string.wrong_credentials_text)
        )
    }

    if (screenState.showWrongAccountTypeError) {
        Alert(
            onDismiss = viewModel::wrongAccountTypeAlertDismissed,
            title = stringResource(id = R.string.wrong_account_type_title),
            text = stringResource(id = R.string.wrong_account_type_text)
        )
    }

    if (screenState.isNeedOpenMain) {
        viewModel.onMainOpened()
        moveToMainRoot()
    }

    if (screenState.isNeedOpenUrl) {
        viewModel.onUrlOpened()
        openUrlScreen()
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "university logo"
            )

            Spacer(modifier = Modifier.height(36.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    value = screenState.login,
                    onValueChange = viewModel::onLoginInputChanged,
                    label = { Text(text = stringResource(id = R.string.login_label)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    supportingText = {
                        if (screenState.showFillLoginError) {
                            Text(
                                text = stringResource(id = R.string.error_wrong_login_or_password),
                                color = Color.Red
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    value = screenState.password,
                    onValueChange = viewModel::onPasswordInputChanged,
                    label = {
                        Text(text = stringResource(id = R.string.password_label))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Go
                    ),
                    keyboardActions = KeyboardActions(
                        onGo = {
                            viewModel.onLogInButtonClicked()
                            keyboardController?.hide()
                        }
                    ),
                    trailingIcon = {
                        IconButton(onClick = viewModel::onPasswordVisibilityButtonClicked) {
                            Icon(
                                painter = painterResource(
                                    id = if (screenState.isPasswordVisible) R.drawable.baseline_visibility_off_24
                                    else R.drawable.baseline_visibility_24
                                ),
                                contentDescription = "Password visibility icon"
                            )
                        }
                    },
                    visualTransformation = if (screenState.isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    supportingText = {
                        if (screenState.showFillPasswordError) {
                            Text(
                                text = stringResource(id = R.string.error_wrong_login_or_password),
                                color = Color.Red
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (screenState.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        onClick = {
                            viewModel.onLogInButtonClicked()
                            keyboardController?.hide()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(56.dp)
                    ) {
                        Text(text = stringResource(id = R.string.button_login))
                    }

                    TextButton(
                        onClick = viewModel::onChangeUrlButtonClicked
                    ) {
                        Text(text = stringResource(id = R.string.action_change_url))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        onError = {},
        moveToMainRoot = {},
        openUrlScreen = {}
    )
}
