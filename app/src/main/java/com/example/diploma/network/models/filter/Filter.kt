package com.example.diploma.network.models.filter

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Filter(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String
)