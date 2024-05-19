package com.example.diploma.network.models.student

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class X(
    @Json(name = "student") val student: Student,
)