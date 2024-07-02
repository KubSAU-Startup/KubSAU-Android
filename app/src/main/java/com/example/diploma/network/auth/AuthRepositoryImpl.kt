package com.example.diploma.network.auth

import com.example.diploma.common.mapDefault
import com.example.diploma.model.SessionInfo
import com.example.diploma.network.ErrorDomain
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(private val service: AuthService) : AuthRepository {

    override suspend fun createNewSession(
        login: String,
        password: String
    ): ApiResult<SessionInfo, ErrorDomain> = withContext(Dispatchers.IO) {
        val parameters = mapOf("login" to login, "password" to password)

        service.createNewSession(parameters).mapDefault()
    }
}
