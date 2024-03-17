package com.example.diploma.network.models.journal.inner

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JournalElement(
    @Json(name = "student") val student: StudentJournal,
    @Json(name = "group") val group: GroupJournal,
    @Json(name = "discipline") val discipline: DisciplineJournal,
    @Json(name = "teacher") val teacher: TeacherJournal,
    @Json(name = "work") val work: WorkJournal
)
