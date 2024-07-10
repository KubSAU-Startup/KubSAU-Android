package com.kubsau.regrab.network.common

import com.kubsau.regrab.model.BackendInfo
import retrofit2.http.GET

interface ApiService {

    @GET("/")
    suspend fun getBackendInfo(): BackendInfo?
}
