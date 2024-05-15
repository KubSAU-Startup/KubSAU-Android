package com.example.diploma.network.models.worktype

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WorkType(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "needTitle") val needTitle: Boolean
)