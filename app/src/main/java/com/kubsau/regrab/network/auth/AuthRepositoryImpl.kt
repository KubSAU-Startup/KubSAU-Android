package com.kubsau.regrab.network.auth

import com.kubsau.regrab.common.mapDefault
import com.kubsau.regrab.model.SessionInfo
import com.kubsau.regrab.network.ErrorDomain
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
