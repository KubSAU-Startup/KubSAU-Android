package com.example.diploma.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.common.storage.AccountConfig
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.network.CODE_WRONG_CREDENTIALS
import com.example.diploma.network.ErrorDomain
import com.example.diploma.network.State
import com.example.diploma.network.auth.AuthUseCase
import com.example.diploma.network.listenValue
import com.example.diploma.network.processState
import com.example.diploma.network.setValue
import com.example.diploma.ui.screens.auth.LoginUseCase
import com.example.diploma.ui.screens.auth.model.LoginScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface LoginViewModel {
    val screenState: StateFlow<LoginScreenState>
    val errorDomain: StateFlow<ErrorDomain?>

    fun onLoginInputChanged(newLogin: String)
    fun onPasswordInputChanged(newPassword: String)

    fun onPasswordVisibilityButtonClicked()

    fun onLogInButtonClicked()
    fun onChangeUrlButtonClicked()

    fun onMainOpened()
    fun onUrlOpened()

    fun wrongAccountTypeAlertDismissed()
    fun wrongCredentialsErrorShown()

    fun apiErrorConsumed()
}

class LoginViewModelImpl(
    private val useCase: AuthUseCase,
    private val loginUseCase: LoginUseCase
) : LoginViewModel, ViewModel() {

    override val screenState = MutableStateFlow(LoginScreenState.EMPTY)

    override val errorDomain = MutableStateFlow<ErrorDomain?>(null)

    override fun onLoginInputChanged(newLogin: String) {
        val newValue = screenState.value.copy(
            login = newLogin,
            showWrongCredentialsError = false
        )

        screenState.update { newValue }
    }

    override fun onPasswordInputChanged(newPassword: String) {
        val newValue = screenState.value.copy(
            password = newPassword,
            showWrongCredentialsError = false
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

        if (login.isEmpty()) {
            val newState = screenState.value.copy(
                showFillLoginError = true
            )
            screenState.update { newState }
        }

        if (password.isEmpty()) {
            val newState = screenState.value.copy(
                showFillPasswordError = true
            )
            screenState.update { newState }
        }

        if (screenState.value.showFillLoginError ||
            screenState.value.showFillPasswordError
        ) {
            return
        }

        loginUseCase.getSessionInfo(login, password)
            .listenValue { state ->
                state.processState(
                    error = { error ->
                        if (error is State.Error.ApiError) {
                            if (error.errorCode == CODE_WRONG_CREDENTIALS) {
                                screenState.setValue { old ->
                                    old.copy(showWrongCredentialsError = true)
                                }
                            } else {
                                val newError = ErrorDomain.parse(
                                    error.errorCode,
                                    error.errorMessage
                                )

                                errorDomain.update { newError }
                            }
                        } else {
                            errorDomain.update { ErrorDomain.UnknownError }
                        }
                    },
                    success = { result ->
                        if (!result.wrongType) {
                            NetworkConfig.token = result.token
                            AccountConfig.departmentId = result.departmentId
                            AccountConfig.departmentName = result.departmentName
                        }

                        screenState.setValue { old ->
                            old.copy(
                                isNeedOpenMain = !result.wrongType,
                                showWrongAccountTypeError = result.wrongType
                            )
                        }
                    }
                )

                screenState.setValue { old ->
                    old.copy(isLoading = state.isLoading())
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

    override fun wrongAccountTypeAlertDismissed() {
        val newState = screenState.value.copy(
            showWrongAccountTypeError = false
        )
        screenState.update { newState }
    }

    override fun wrongCredentialsErrorShown() {
        val newState = screenState.value.copy(
            showWrongCredentialsError = false
        )
        screenState.update { newState }
    }

    override fun apiErrorConsumed() {
        errorDomain.update { null }
    }
}
