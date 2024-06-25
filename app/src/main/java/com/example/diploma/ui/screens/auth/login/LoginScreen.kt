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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diploma.R
import com.example.diploma.model.Account
import com.example.diploma.network.ApiError
import com.example.diploma.network.ApiResponse
import com.example.diploma.network.auth.AuthRepositoryImpl
import com.example.diploma.network.auth.AuthService
import com.slack.eithernet.ApiResult
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onError: (String) -> Unit,
    moveToMainRoot: () -> Unit,
    openUrlScreen: () -> Unit,
    viewModel: LoginViewModelImpl = koinViewModel()
) {
    val screenState by viewModel.screenState.collectAsState()

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
                    label = {
                        Text(text = stringResource(id = R.string.login_label))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    supportingText = {
                        screenState.error?.let {
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
                        onGo = { viewModel.onLogInButtonClicked() }
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
                        screenState.error?.let {
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
                        onClick = viewModel::onLogInButtonClicked,
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
    val viewModel = LoginViewModelImpl(
        AuthRepositoryImpl(object : AuthService {
            override suspend fun createNewSession(authInfo: Map<String, String>): ApiResult<ApiResponse<Account>, ApiError> =
                ApiResult.success(ApiResponse(null, null, true))
        })
    )

    LoginScreen(
        onError = {},
        viewModel = viewModel,
        moveToMainRoot = {},
        openUrlScreen = {}
    )
}
