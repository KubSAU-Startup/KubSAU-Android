package com.example.diploma.network.auth

import com.example.diploma.model.SessionInfo
import com.example.diploma.network.State
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    fun getSessionInfo(login: String, password: String): Flow<State<SessionInfo>>
}
