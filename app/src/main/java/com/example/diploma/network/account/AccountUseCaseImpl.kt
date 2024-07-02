package com.example.diploma.network.account

import com.example.diploma.common.mapToState
import com.example.diploma.model.Account
import com.example.diploma.network.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccountUseCaseImpl(private val repository: AccountRepository) : AccountUseCase {

    override fun getAccountInfo(): Flow<State<Account>> = flow {
        emit(State.Loading)

        val newState = repository.getAccountInfo().mapToState()
        emit(newState)
    }
}
