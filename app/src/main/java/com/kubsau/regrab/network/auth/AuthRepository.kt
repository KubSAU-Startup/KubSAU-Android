package com.kubsau.regrab.network.auth

import com.kubsau.regrab.model.SessionInfo
import com.kubsau.regrab.network.ErrorDomain
import com.slack.eithernet.ApiResult

interface AuthRepository {

    suspend fun createNewSession(
        login: String,
        password: String
    ): ApiResult<SessionInfo, ErrorDomain>
}
