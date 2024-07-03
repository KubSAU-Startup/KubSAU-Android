package com.kubsau.regrab.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntryElement(
    @Json(name = "student") val student: StudentEntry,
    @Json(name = "group") val group: GroupEntry,
    @Json(name = "discipline") val discipline: DisciplineEntry,
    @Json(name = "employee") val employee: Employee,
    @Json(name = "work") val work: WorkEntry,
    @Json(name = "department") val department: DepartmentEntry,
)
