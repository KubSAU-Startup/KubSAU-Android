package com.example.diploma.network.models.back

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BackendInfo(
    @Json(name = "version") val version: String,
)
