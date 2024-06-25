package com.example.diploma.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.common.storage.AccountConfig
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.network.auth.AuthRepository
import com.example.diploma.ui.screens.auth.model.LoginScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface LoginViewModel {
    val screenState: StateFlow<LoginScreenState>

    fun onLoginInputChanged(newLogin: String)
    fun onPasswordInputChanged(newPassword: String)

    fun onPasswordVisibilityButtonClicked()

    fun onLogInButtonClicked()
    fun onChangeUrlButtonClicked()

    fun onMainOpened()
    fun onUrlOpened()
}

class LoginViewModelImpl(private val repository: AuthRepository) : LoginViewModel, ViewModel() {

    override val screenState = MutableStateFlow(LoginScreenState.EMPTY)

    override fun onLoginInputChanged(newLogin: String) {
        val newValue = screenState.value.copy(
            login = newLogin,
            error = null
        )

        screenState.update { newValue }
    }

    override fun onPasswordInputChanged(newPassword: String) {
        val newValue = screenState.value.copy(
            password = newPassword,
            error = null
        )

        screenState.update { newValue }
    }

    override fun onPasswordVisibilityButtonClicked() {
        val newValue = screenState.value.copy(
            isPasswordVisible = !screenState.value.isPasswordVisible
        )

        screenState.update { newValue }
    }

    override fun onLogInButtonClicked() {
        val login = screenState.value.login.trim()
        val password = screenState.value.password.trim()

        if (login.isEmpty() || password.isEmpty()) {
            viewModelScope.launch(Dispatchers.Main) {
                screenState.emit(
                    screenState.value.copy(
                        error = "Fill the fields"
                    )
                )
            }
            return
        }

        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                screenState.emit(screenState.value.copy(isLoading = true))
            }

            val response = repository.createNewSession(
                login = login,
                password = password
            )

            withContext(Dispatchers.Main) {
                val newScreenState: LoginScreenState =
                    if (response == null) {
                        screenState.value.copy(
                            error = "Wrong credentials",
                            isLoading = false
                        )
                    } else {
                        NetworkConfig.token = response.token
                        AccountConfig.departmentList = response.departmentIds

                        screenState.value.copy(
                            isLoading = false,
                            isNeedOpenMain = true
                        )
                    }

                screenState.emit(newScreenState)
            }
        }
    }

    override fun onChangeUrlButtonClicked() {
        viewModelScope.launch(Dispatchers.Main) {
            screenState.emit(screenState.value.copy(isNeedOpenUrl = true))
        }
    }

    override fun onMainOpened() {
        viewModelScope.launch(Dispatchers.Main) {
            screenState.emit(screenState.value.copy(isNeedOpenMain = false))
        }
    }

    override fun onUrlOpened() {
        viewModelScope.launch(Dispatchers.Main) {
            screenState.emit(screenState.value.copy(isNeedOpenUrl = false))
        }
    }
}
