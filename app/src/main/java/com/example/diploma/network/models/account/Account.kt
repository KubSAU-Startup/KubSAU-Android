package com.example.diploma.network.models.account

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Account(
    @Json(name = "userId") val id: Int,
    @Json(name = "accessToken") val token: String,
    @Json(name = "isAdmin") val isAdmin: Boolean?= null,
)