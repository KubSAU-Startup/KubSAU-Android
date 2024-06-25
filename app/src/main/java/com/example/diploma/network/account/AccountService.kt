package com.example.diploma.network.account

import com.example.diploma.model.Account
import com.example.diploma.network.ApiError
import com.example.diploma.network.ApiResponse
import com.slack.eithernet.ApiResult
import retrofit2.http.GET

interface AccountService {

    @GET("/account")
    suspend fun getAccountInfo(): ApiResult<ApiResponse<Account>, ApiError>
}
