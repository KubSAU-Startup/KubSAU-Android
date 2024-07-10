package com.kubsau.regrab.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DisciplineEntry(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "departmentId") val departmentId: Int,
)
