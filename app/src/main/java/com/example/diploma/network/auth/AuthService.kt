package com.example.diploma.network.auth

import com.example.diploma.network.ApiError
import com.example.diploma.network.ApiResponse
import com.example.diploma.model.SessionInfo
import com.slack.eithernet.ApiResult
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @POST("/auth")
    @FormUrlEncoded
    suspend fun createNewSession(@FieldMap authInfo: Map<String, String>): ApiResult<ApiResponse<SessionInfo>, ApiError>
}
