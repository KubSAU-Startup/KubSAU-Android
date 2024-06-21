package com.example.diploma.ui.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.diploma.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    moveToMainRoot: () -> Unit
) {

    if (viewModel.successfulLogin)
        moveToMainRoot()

    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    val visibilityIcon = if (passwordVisibility)
        painterResource(id = R.drawable.baseline_visibility_24)
    else painterResource(id = R.drawable.baseline_visibility_off_24)

    if (viewModel.successfulLogin)
        moveToMainRoot()

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "university logo"
            )

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = viewModel.loginInfo,
                    onValueChange = { viewModel.infoLogin(login = it) },
                    label = {
                        Text(text = stringResource(id = R.string.login_label))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    supportingText = {
                        if (viewModel.showError)
                            Text(
                                text = stringResource(id = R.string.error_wrong_login_or_password),
                                color = Color.Red
                            )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.passwordInfo,
                    onValueChange = { viewModel.infoPassword(password = it) },
                    label = {
                        Text(text = stringResource(id = R.string.password_label))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Go
                    ),
                    keyboardActions = KeyboardActions(onGo = {
                        viewModel.auth()
                    }),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                painter = visibilityIcon,
                                contentDescription = "Password visibility icon"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    supportingText = {
                        if (viewModel.showError)
                            Text(
                                text = stringResource(id = R.string.error_wrong_login_or_password),
                                color = Color.Red
                            )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    enabled = viewModel.buttonActive,
                    onClick = {
                        viewModel.auth()
                    }) {
                    Text(text = stringResource(id = R.string.button_login))
                }
            }
        }
    }
}
