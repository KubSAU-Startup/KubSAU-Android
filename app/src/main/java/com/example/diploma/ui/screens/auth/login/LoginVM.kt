package com.example.diploma.ui.screens.auth.login

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

    var buttonActive by mutableStateOf(value = true)

    fun auth() {
        viewModelScope.launch {

            buttonActive = false

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

            buttonActive = true
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