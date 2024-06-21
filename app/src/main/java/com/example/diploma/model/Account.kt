package com.example.diploma.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Account(
    @Json(name = "userId") val id: Int,
    @Json(name = "accessToken") val token: String,
    @Json(name = "facultyId") val facultyId: Int? = null,
    @Json(name = "departmentIds") val departmentIds: List<Int>
)
