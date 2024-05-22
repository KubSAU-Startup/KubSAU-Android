package com.example.diploma.network.models.work

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Work(
    @Json(name = "id") val id: Int,
    @Json(name = "disciplineId") val disciplineId: Int,
    @Json(name = "studentId") val studentId: Int,
    @Json(name = "registrationDate") val registrationDate: Long,
    @Json(name = "title") val title: String? = null,
    @Json(name = "workTypeId") val workTypeId: Int,
    @Json(name = "employeeId") val employeeId: Int,
    @Json(name = "departmentId") val departmentId: Int,
)