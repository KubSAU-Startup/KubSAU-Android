package com.kubsau.regrab.network.account

import com.kubsau.regrab.model.Account
import com.kubsau.regrab.network.ErrorDomain
import com.slack.eithernet.ApiResult

interface AccountRepository {

    suspend fun getAccountInfo(): ApiResult<Account, ErrorDomain>
}
