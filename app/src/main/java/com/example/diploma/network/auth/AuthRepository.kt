package com.example.diploma.network.auth

import com.example.diploma.model.SessionInfo

interface AuthRepository {

    suspend fun createNewSession(login: String, password: String): SessionInfo?
}
