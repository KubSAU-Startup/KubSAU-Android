package com.example.diploma.network.models.journal.inner

import com.example.diploma.network.models.worktype.WorkType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WorkJournal(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: WorkType,
    @Json(name = "registrationDate") val registrationDate: Long,
    @Json(name = "title") val title: String? = null,
    @Json(name = "employeeId") val employeeId: Int,
)