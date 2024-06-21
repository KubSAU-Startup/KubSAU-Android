package com.example.diploma.ui.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.common.EMPTY_STRING
import com.example.diploma.common.intToString
import com.example.diploma.common.storage.AccountConfig
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.network.auth.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

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

            val response = repository.createNewSession(
                login = loginInfo.trim(),
                password = passwordInfo.trim()
            )

            if (response != null) {
                NetworkConfig.token = response.token
                AccountConfig.departmentList = response.departmentIds.intToString()

                showError = false
                successfulLogin = true
            } else {
                // TODO: 21/06/2024, Danil Nikolaev: show error
                showError = true
                successfulLogin = false
            }

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
