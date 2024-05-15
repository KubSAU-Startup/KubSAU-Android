package com.example.diploma.network.models.student

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Student(
    @Json(name = "id") val id: Int,
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "middleName") val middleName: String,
    @Json(name = "groupId") val groupId: Int = 1,
    @Json(name = "statusId") val statusId: Int = 1,
)