package com.example.diploma.network.models.account

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountInfo(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: Int,
    @Json(name = "login") val login: String,
    @Json(name = "departmentIds") val departmentIds: List<Int>
)