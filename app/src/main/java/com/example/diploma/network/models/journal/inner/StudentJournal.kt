package com.example.diploma.network.models.journal.inner

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StudentJournal(
    @Json(name = "fullName") val fullName: String,
    @Json(name = "status") val status: Int
)