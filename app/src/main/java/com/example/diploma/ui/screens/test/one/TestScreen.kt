package com.example.diploma.ui.screens.test.one

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.diploma.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun TestScreen() {
    val viewModel = koinViewModel<TestVM>()

    var loginInfo by remember {
        mutableStateOf("")
    }

    var passwordInfo by remember {
        mutableStateOf("")
    }

    val openDialog = remember {
        mutableStateOf(false)
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = { openDialog.value = false },
            dismissButton = { openDialog.value = false },
            title = {
                Text(text = "test")
            },
            text = {
                Text(text = "sex")
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row {
            Text(text = viewModel.responseText)
        }
        Row {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TextField(
                    value = loginInfo,
                    onValueChange = {
                        loginInfo = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.login_label))
                    },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                TextField(
                    value = passwordInfo,
                    onValueChange = {
                        passwordInfo = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.password_label))
                    }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = {
                        openDialog.value = true
                    }) {
                        Text(text = stringResource(id = R.string.button_login))
                    }

                    Button(onClick = {
//                        viewModel.getAccountInfo()
                    }) {
                        Text(text = stringResource(id = R.string.button_account))
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = {
//                        viewModel.getJournal()
                    }) {
                        Text(text = stringResource(id = R.string.button_get_users))
                    }

                    Button(onClick = {
                    }) {
                        Text(text = stringResource(id = R.string.button_set_user))
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = {
                    }) {
                        Text(text = stringResource(id = R.string.button_delete_user))
                    }
                }
            }
        }
    }
}