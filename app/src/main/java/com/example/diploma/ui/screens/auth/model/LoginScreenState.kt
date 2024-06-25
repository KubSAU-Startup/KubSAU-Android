package com.example.diploma.ui.screens.auth.model

data class LoginScreenState(
    val isLoading: Boolean,
    val login: String,
    val password: String,
    val error: String?,
    val isNeedOpenMain: Boolean,
    val isNeedOpenUrl: Boolean,
    val isPasswordVisible: Boolean
) {

    companion object {
        val EMPTY: LoginScreenState = LoginScreenState(
            isLoading = false,
            login = "",
            password = "",
            error = null,
            isNeedOpenMain = false,
            isNeedOpenUrl = false,
            isPasswordVisible = false
        )
    }
}
