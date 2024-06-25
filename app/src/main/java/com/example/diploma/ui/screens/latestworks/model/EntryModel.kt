package com.example.diploma.ui.screens.latestworks.model

import com.example.diploma.model.EntryElement
import java.text.SimpleDateFormat

data class EntryModel(
    val id: Int,
    val discipline: String,
    val title: String?,
    val student: String,
    val group: String,
    val teacher: String,
    val date: String,
    val workType: String
)

fun EntryElement.toModel(dateFormatter: SimpleDateFormat): EntryModel = EntryModel(
    id = work.id,
    discipline = discipline.title,
    title = work.title.orEmpty().trim().ifEmpty { null },
    student = student.fullName,
    group = group.title,
    teacher = with(employee) {
        middleName?.let {
            "%s %s %s".format(lastName, firstName, middleName)
        } ?: "%s %s".format(lastName, firstName)
    },
    date = dateFormatter.format(work.registrationDate),
    workType = work.type.title
)
