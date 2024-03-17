package com.example.diploma.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorModel(
    @Json(name = "code") val errorCode: Int,
    @Json(name = "message") val errorMessage: String
)
