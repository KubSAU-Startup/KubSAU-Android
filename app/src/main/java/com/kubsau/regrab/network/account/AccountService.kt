package com.kubsau.regrab.network.account

import com.kubsau.regrab.model.Account
import com.kubsau.regrab.network.ErrorModel
import com.kubsau.regrab.network.ResponseModel
import com.slack.eithernet.ApiResult
import retrofit2.http.GET

interface AccountService {

    @GET("/account")
    suspend fun getAccountInfo(): ApiResult<ResponseModel<Account>, ErrorModel>
}
