package com.example.diploma.network.account

import com.example.diploma.common.mapDefault
import com.example.diploma.model.Account
import com.example.diploma.network.ErrorDomain
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepositoryImpl(private val service: AccountService) : AccountRepository {

    override suspend fun getAccountInfo(): ApiResult<Account, ErrorDomain> =
        withContext(Dispatchers.IO) { service.getAccountInfo().mapDefault() }
}
