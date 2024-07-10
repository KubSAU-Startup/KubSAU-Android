package com.kubsau.regrab.network.auth

import com.kubsau.regrab.common.mapToState
import com.kubsau.regrab.model.SessionInfo
import com.kubsau.regrab.network.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthUseCaseImpl(private val repository: AuthRepository) : AuthUseCase {

    override fun getSessionInfo(login: String, password: String): Flow<State<SessionInfo>> = flow {
        emit(State.Loading)

        val newState = repository.createNewSession(login, password).mapToState()
        emit(newState)
    }
}
