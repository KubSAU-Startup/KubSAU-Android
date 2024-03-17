package com.example.diploma.network.models.filter

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Filter(
    @Json(name = "workTypes") val workTypes: List<FilterElement> = listOf(),
    @Json(name = "disciplines") val disciplines: List<FilterElement> = listOf(),
    @Json(name = "teachers") val teachers: List<FilterElement> = listOf(),
    @Json(name = "groups") val groups: List<FilterElement> = listOf(),
    @Json(name = "departments") val departments: List<FilterElement> = listOf(),
)

@JsonClass(generateAdapter = true)
data class FilterElement(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String
)