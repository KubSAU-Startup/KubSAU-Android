package com.example.diploma.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseSuccess<T>(
    @Json(name = "success") val success: Boolean = true,
    @Json(name = "error") val error: ErrorModel? = null,
    @Json(name = "response") val response: T
)

@JsonClass(generateAdapter = true)
data class ResponseFail<T>(
    @Json(name = "success") val success: Boolean = false,
    @Json(name = "error") val error: ErrorModel,
    @Json(name = "response") val response: T? = null
)
