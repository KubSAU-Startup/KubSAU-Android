package com.example.diploma.network.models.journal.inner

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DepartmentJournal(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "phone") val phone: String,
)