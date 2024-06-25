package com.example.diploma.network.account

import com.example.diploma.model.Account

interface AccountRepository {

    suspend fun getAccountInfo(): Account?
}
