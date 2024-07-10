package com.kubsau.regrab.network.account

import com.kubsau.regrab.model.Account
import com.kubsau.regrab.network.State
import kotlinx.coroutines.flow.Flow

interface AccountUseCase {

    fun getAccountInfo(): Flow<State<Account>>
}
