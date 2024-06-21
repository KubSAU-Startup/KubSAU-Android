package com.example.diploma.network.common

import com.example.diploma.model.BackendInfo

interface ApiRepository {

    suspend fun getBackendInfo(): BackendInfo?
}
