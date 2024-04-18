package com.example.diploma.network.models.work

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Work(
    @Json(name = "disciplineId") val disciplineId: Int,
    @Json(name = "studentId") val studentId: Int,
    @Json(name = "registrationDate") val registrationDate: Int = (System.currentTimeMillis() / 1000L).toInt(),
    @Json(name = "title") val title: String? = null,
)