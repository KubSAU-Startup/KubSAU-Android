package com.example.diploma.network.models.journal

import com.example.diploma.network.models.journal.inner.JournalElement
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Journal(
    @Json(name = "count") val count: Int = 0,
    @Json(name = "offset") val offset: Int = 0,
    @Json(name = "journal") val journal: List<JournalElement> = listOf()
)
