package com.example.diploma.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    @Json(name = "error") val error: ApiError?,
    @Json(name = "response") val response: T?,
    @Json(name = "success") val success: Boolean
) {
    val isSuccessful get() = success

    fun requireResponse(): T = requireNotNull(response)
}
