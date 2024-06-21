package com.example.diploma.network.auth

import com.example.diploma.model.Account

interface AuthRepository {

    suspend fun createNewSession(login: String, password: String): Account?
}
