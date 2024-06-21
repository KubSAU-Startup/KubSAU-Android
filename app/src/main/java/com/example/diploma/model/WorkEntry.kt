package com.example.diploma.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WorkEntry(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: WorkType,
    @Json(name = "registrationDate") val registrationDate: Long,
    @Json(name = "title") val title: String? = null,
    @Json(name = "employeeId") val employeeId: Int,
)
