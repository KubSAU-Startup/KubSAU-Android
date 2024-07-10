package com.kubsau.regrab.network.auth

import com.kubsau.regrab.model.SessionInfo
import com.kubsau.regrab.network.State
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    fun getSessionInfo(login: String, password: String): Flow<State<SessionInfo>>
}
