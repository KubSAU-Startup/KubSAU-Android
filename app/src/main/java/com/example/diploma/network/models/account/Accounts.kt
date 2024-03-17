package com.example.diploma.network.models.account

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Accounts(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: Int,
    @Json(name = "email") val email: String,
    @Json(name = "departmentId") val departmentId: Int
)