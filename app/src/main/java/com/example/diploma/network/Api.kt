package com.example.diploma.network

import com.example.diploma.network.calladapter.NetworkResponse
import com.example.diploma.network.models.ResponseFail
import com.example.diploma.network.models.ResponseSuccess
import com.example.diploma.network.models.account.Account
import com.example.diploma.network.models.account.Accounts
import com.example.diploma.network.models.filter.Filter
import com.example.diploma.network.models.journal.Journal
import retrofit2.http.GET
import retrofit2.http.QueryMap

typealias Response<T> = NetworkResponse<ResponseSuccess<T>, ResponseFail<T>>

interface Api {
    @GET("auth")
    suspend fun auth(@QueryMap authInfo: Map<String, String>): Response<Account>

    @GET("account")
    suspend fun accountInfo(): Response<Accounts>

    @GET("/journals")
    suspend fun journals(@QueryMap filters: Map<String, Int>): Response<Journal>

    @GET("/journals/filters")
    suspend fun filters(): Response<Filter>
}