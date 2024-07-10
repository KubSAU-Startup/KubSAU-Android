package com.kubsau.regrab.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Student(
    @Json(name = "id") val id: Int,
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "middleName") val middleName: String?,
    @Json(name = "groupId") val groupId: Int,
    @Json(name = "status") val status: Int,
) {

    val fullName: String
        get() = if (middleName == null) {
            "$firstName $lastName"
        } else {
            "$lastName $firstName $middleName"
        }
}
