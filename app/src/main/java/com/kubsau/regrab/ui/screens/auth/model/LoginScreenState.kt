package com.kubsau.regrab.ui.screens.auth.model

data class LoginScreenState(
    val isLoading: Boolean,
    val login: String,
    val password: String,
    val isNeedOpenMain: Boolean,
    val isNeedOpenUrl: Boolean,
    val isPasswordVisible: Boolean,
    val showFillLoginError: Boolean,
    val showFillPasswordError: Boolean,
    val showWrongAccountTypeError: Boolean,
    val showWrongCredentialsError: Boolean
) {

    companion object {
        val EMPTY: LoginScreenState = LoginScreenState(
            isLoading = false,
            login = "",
            password = "",
            isNeedOpenMain = false,
            isNeedOpenUrl = false,
            isPasswordVisible = false,
            showFillLoginError = false,
            showFillPasswordError = false,
            showWrongAccountTypeError = false,
            showWrongCredentialsError = false
        )
    }
}
