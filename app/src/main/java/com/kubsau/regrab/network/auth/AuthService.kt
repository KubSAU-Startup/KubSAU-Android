package com.kubsau.regrab.network.auth

import com.kubsau.regrab.network.ErrorModel
import com.kubsau.regrab.network.ResponseModel
import com.kubsau.regrab.model.SessionInfo
import com.slack.eithernet.ApiResult
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @POST("/auth")
    @FormUrlEncoded
    suspend fun createNewSession(@FieldMap authInfo: Map<String, String>): ApiResult<ResponseModel<SessionInfo>, ErrorModel>
}
