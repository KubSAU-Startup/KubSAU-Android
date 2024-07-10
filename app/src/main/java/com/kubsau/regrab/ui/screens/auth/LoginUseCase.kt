package com.kubsau.regrab.ui.screens.auth

import com.kubsau.regrab.network.State
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    fun getSessionInfo(login: String, password: String): Flow<State<LoginInfo>>
}
