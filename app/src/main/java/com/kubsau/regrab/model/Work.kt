package com.kubsau.regrab.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Work(
    @Json(name = "id") val id: Int,
    @Json(name = "disciplineId") val disciplineId: Int,
    @Json(name = "studentId") val studentId: Int,
    @Json(name = "title") val title: String?,
    @Json(name = "workTypeId") val workTypeId: Int,
    @Json(name = "employeeId") val employeeId: Int
)
