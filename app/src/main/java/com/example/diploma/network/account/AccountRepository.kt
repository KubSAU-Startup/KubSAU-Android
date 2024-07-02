package com.example.diploma.network.account

import com.example.diploma.model.Account
import com.example.diploma.network.ErrorDomain
import com.slack.eithernet.ApiResult

interface AccountRepository {

    suspend fun getAccountInfo(): ApiResult<Account, ErrorDomain>
}
