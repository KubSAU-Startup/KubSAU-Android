package com.example.diploma.network.account

import com.example.diploma.model.Account
import com.example.diploma.network.State
import kotlinx.coroutines.flow.Flow

interface AccountUseCase {

    fun getAccountInfo(): Flow<State<Account>>
}
