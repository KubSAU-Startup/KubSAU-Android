package com.example.diploma.network.auth

import com.example.diploma.common.mapToState
import com.example.diploma.model.SessionInfo
import com.example.diploma.network.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthUseCaseImpl(private val repository: AuthRepository) : AuthUseCase {

    override fun getSessionInfo(login: String, password: String): Flow<State<SessionInfo>> = flow {
        emit(State.Loading)

        val newState = repository.createNewSession(login, password).mapToState()
        emit(newState)
    }
}
