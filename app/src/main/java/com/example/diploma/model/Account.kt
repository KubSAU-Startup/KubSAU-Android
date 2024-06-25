package com.example.diploma.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Account(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: Int,
    @Json(name = "login") val login: String,
    @Json(name = "selectedDepartmentId") val selectedDepartmentId: Int?,
    @Json(name = "faculty") val faculty: Filter?,
    @Json(name = "departments") val departments: List<DepartmentEntry>
)
