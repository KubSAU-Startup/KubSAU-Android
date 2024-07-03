package com.kubsau.regrab.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetLatestWorksResponse(
    @Json(name = "count") val count: Int,
    @Json(name = "offset") val offset: Int,
    @Json(name = "entries") val entries: List<EntryElement>
)
