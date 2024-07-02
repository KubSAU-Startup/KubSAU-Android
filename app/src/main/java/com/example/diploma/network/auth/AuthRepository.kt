package com.example.diploma.network.auth

import com.example.diploma.model.SessionInfo
import com.example.diploma.network.ErrorDomain
import com.slack.eithernet.ApiResult

interface AuthRepository {

    suspend fun createNewSession(
        login: String,
        password: String
    ): ApiResult<SessionInfo, ErrorDomain>
}
