package com.kubsau.regrab.network.account

import com.kubsau.regrab.common.mapToState
import com.kubsau.regrab.model.Account
import com.kubsau.regrab.network.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccountUseCaseImpl(private val repository: AccountRepository) : AccountUseCase {

    override fun getAccountInfo(): Flow<State<Account>> = flow {
        emit(State.Loading)

        val newState = repository.getAccountInfo().mapToState()
        emit(newState)
    }
}
