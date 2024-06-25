package com.example.diploma.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Employee(
    @Json(name = "id") val id: Int,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "firstName") val firstName: String,
    @Json(name = "middleName") val middleName: String?,
    @Json(name = "email") val email: String,
    @Json(name = "type") val typeId: Int,
)
