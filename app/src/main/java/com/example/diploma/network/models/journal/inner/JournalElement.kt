package com.example.diploma.network.models.journal.inner

import com.example.diploma.network.models.employee.Employee
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JournalElement(
    @Json(name = "student") val student: StudentJournal,
    @Json(name = "group") val group: GroupJournal,
    @Json(name = "discipline") val discipline: DisciplineJournal,
    @Json(name = "employee") val employee: Employee,
    @Json(name = "work") val work: WorkJournal,
    @Json(name = "department") val department: DepartmentJournal,
)
