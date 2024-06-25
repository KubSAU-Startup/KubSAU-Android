package com.example.diploma.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.common.storage.AccountConfig
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.network.account.AccountRepository
import com.example.diploma.network.auth.AuthRepository
import com.example.diploma.network.employees.EmployeeRepository
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

    fun wrongAccountTypeAlertDismissed()
}

class LoginViewModelImpl(
    private val repository: AuthRepository,
    private val accountRepository: AccountRepository,
    private val employeeRepository: EmployeeRepository
) : LoginViewModel, ViewModel() {

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

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                screenState.emit(screenState.value.copy(isLoading = true))
            }

            val sessionInfo = repository.createNewSession(
                login = login,
                password = password
            )

            if (sessionInfo == null) {
                // TODO: 25/06/2024, Danil Nikolaev: show error
                val newState = screenState.value.copy(
                    error = "Wrong credentials",
                    isLoading = false
                )
                screenState.update { newState }
            } else {
                NetworkConfig.token = sessionInfo.token
                AccountConfig.departmentId = sessionInfo.departmentIds.first()

                val accountInfo = accountRepository.getAccountInfo()
                if (accountInfo == null) {
                    // TODO: 25/06/2024, Danil Nikolaev: show error
                    val newState = screenState.value.copy(
                        error = "not loaded wtf",
                        isLoading = false
                    )
                    screenState.update { newState }
                } else {
                    if (accountInfo.type != 3) {
                        val newState = screenState.value.copy(
                            showWrongAccountTypeError = true,
                            isLoading = false
                        )
                        screenState.update { newState }
                    } else {
                        AccountConfig.departmentName = accountInfo.departments.first().title

                        val employee = employeeRepository.getEmployeeById(accountInfo.employeeId)
                        if (employee == null) {
                            // TODO: 25/06/2024, Danil Nikolaev: show error
                            val newState = screenState.value.copy(
                                error = "not loaded wtf",
                                isLoading = false
                            )
                            screenState.update { newState }
                        } else {
                            AccountConfig.fullName = with(employee) {
                                middleName?.let {
                                    "%s %s %s".format(
                                        lastName,
                                        firstName,
                                        middleName
                                    )
                                } ?: "%s %s".format(lastName, firstName)
                            }

                            val newState = screenState.value.copy(
                                isLoading = false,
                                isNeedOpenMain = true
                            )
                            screenState.update { newState }
                        }
                    }
                }
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
}
