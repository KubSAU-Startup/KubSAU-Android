package com.kubsau.regrab.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DisciplineFilter(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
)
