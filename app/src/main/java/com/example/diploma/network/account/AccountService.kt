package com.example.diploma.network.account

import com.example.diploma.model.Account
import com.example.diploma.network.ErrorModel
import com.example.diploma.network.ResponseModel
import com.slack.eithernet.ApiResult
import retrofit2.http.GET

interface AccountService {

    @GET("/account")
    suspend fun getAccountInfo(): ApiResult<ResponseModel<Account>, ErrorModel>
}
