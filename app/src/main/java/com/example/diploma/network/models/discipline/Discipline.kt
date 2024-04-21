package com.example.diploma.network.models.discipline

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Discipline(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "workTypeId") val workTypeId: Int,
)
