package com.example.diploma.network.common

import com.example.diploma.model.BackendInfo
import retrofit2.http.GET

interface ApiService {

    @GET("/")
    suspend fun getBackendInfo(): BackendInfo?
}
