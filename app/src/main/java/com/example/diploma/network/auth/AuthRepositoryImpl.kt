package com.example.diploma.network.auth

import com.example.diploma.common.foldOnSuccess
import com.example.diploma.model.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(private val service: AuthService) : AuthRepository {

    override suspend fun createNewSession(
        login: String,
        password: String
    ): Account? = withContext(Dispatchers.IO) {
        val parameters = mapOf("login" to login, "password" to password)
        service.createNewSession(parameters).foldOnSuccess()
    }
}
