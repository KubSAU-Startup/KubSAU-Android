package com.kubsau.regrab.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Account(
    @Json(name = "id") val id: Int,
    @Json(name = "employeeId") val employeeId: Int,
    @Json(name = "type") val type: Int,
    @Json(name = "login") val login: String,
    @Json(name = "selectedDepartmentId") val selectedDepartmentId: Int?,
    @Json(name = "departments") val departments: List<DepartmentEntry>
)
