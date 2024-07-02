package com.example.diploma.ui.screens.auth

import com.example.diploma.network.State
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    fun getSessionInfo(login: String, password: String): Flow<State<LoginInfo>>
}
