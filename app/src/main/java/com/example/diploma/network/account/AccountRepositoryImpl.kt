package com.example.diploma.network.account

import com.example.diploma.common.foldOnSuccess
import com.example.diploma.model.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepositoryImpl(private val service: AccountService) : AccountRepository {

    override suspend fun getAccountInfo(): Account? = withContext(Dispatchers.IO) {
        service.getAccountInfo().foldOnSuccess()
    }
}
