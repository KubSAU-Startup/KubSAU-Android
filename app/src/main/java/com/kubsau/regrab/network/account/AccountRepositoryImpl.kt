package com.kubsau.regrab.network.account

import com.kubsau.regrab.common.mapDefault
import com.kubsau.regrab.model.Account
import com.kubsau.regrab.network.ErrorDomain
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepositoryImpl(private val service: AccountService) : AccountRepository {

    override suspend fun getAccountInfo(): ApiResult<Account, ErrorDomain> =
        withContext(Dispatchers.IO) { service.getAccountInfo().mapDefault() }
}
