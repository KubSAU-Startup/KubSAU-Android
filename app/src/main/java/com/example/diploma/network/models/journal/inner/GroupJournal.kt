package com.example.diploma.network.models.journal.inner

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GroupJournal(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "majorId") val majorId: Int
)