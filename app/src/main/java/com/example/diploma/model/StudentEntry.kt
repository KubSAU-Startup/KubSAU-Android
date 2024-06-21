package com.example.diploma.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StudentEntry(
    @Json(name = "fullName") val fullName: String,
    @Json(name = "status") val status: Filter
)