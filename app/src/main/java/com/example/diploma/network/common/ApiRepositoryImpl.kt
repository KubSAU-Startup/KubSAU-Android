package com.example.diploma.network.common

import com.example.diploma.model.BackendInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiRepositoryImpl(private val service: ApiService) : ApiRepository {

    override suspend fun getBackendInfo(): BackendInfo? = withContext(Dispatchers.IO) {
        runCatching { service.getBackendInfo() }
            .fold(
                onSuccess = { response -> requireNotNull(response) },
                onFailure = { null }
            )
    }
}
