package com.kubsau.regrab.network.common

import com.kubsau.regrab.model.BackendInfo

interface ApiRepository {

    suspend fun getBackendInfo(): BackendInfo?
}
