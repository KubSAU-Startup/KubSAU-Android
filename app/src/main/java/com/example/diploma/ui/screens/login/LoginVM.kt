package com.example.diploma.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.common.EMPTY_STRING
import com.example.diploma.network.NetworkRepo
import kotlinx.coroutines.launch

class LoginVM(private val repo: NetworkRepo) : ViewModel() {

    var loginInfo by mutableStateOf(value = EMPTY_STRING)
        private set

    var passwordInfo by mutableStateOf(value = EMPTY_STRING)
        private set

    var showError by mutableStateOf(value = false)
        private set

    var successfulLogin by mutableStateOf(value = false)

    fun auth() {
        viewModelScope.launch {
            if (passwordInfo == EMPTY_STRING) {
                showError = true
                return@launch
            }

            if (loginInfo == EMPTY_STRING) {
                showError = true
                return@launch
            }

            val response = repo.auth(
                mapOf(
                    "login" to loginInfo.trim(),
                    "password" to passwordInfo.trim()
                )
            )

            showError = !response

            successfulLogin = response
        }
    }

    fun infoLogin(login: String) {
        loginInfo = login
        showError = false
    }

    fun infoPassword(password: String) {
        passwordInfo = password
        showError = false
    }
}