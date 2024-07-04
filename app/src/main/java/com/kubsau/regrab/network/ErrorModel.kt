package com.kubsau.regrab.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorModel(
    @Json(name = "code") val code: Int,
    @Json(name = "message") val message: String
) {
    fun toDomain(): ErrorDomain = ErrorDomain.parse(code, message)
}
