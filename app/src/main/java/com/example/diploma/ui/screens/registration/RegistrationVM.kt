package com.example.diploma.ui.screens.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.common.EMPTY_STRING
import com.example.diploma.network.NetworkRepo
import com.example.diploma.network.models.work.Work
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class RegistrationVM(private val repo: NetworkRepo) : ViewModel() {

    private var disciplineId by mutableIntStateOf(0)
    private var studentId by mutableIntStateOf(0)
    private var workTypeId by mutableIntStateOf(0)
    var editable by mutableStateOf(false)
        private set

    var disciplineDisplay by mutableStateOf(EMPTY_STRING)
        private set
    var studentDisplay by mutableStateOf(EMPTY_STRING)
        private set
    var workTypeDisplay by mutableStateOf(EMPTY_STRING)
        private set
    var workTitle by mutableStateOf(EMPTY_STRING)

    var student by mutableStateOf(EMPTY_STRING)

    fun fetchData(data: String) {

        val (student, discipline, edit) = data.split(',').map { it.toInt() }
        studentId = student
        disciplineId = discipline
        editable = edit.toBoolean()

        viewModelScope.launch {
            val executeTime = measureTimeMillis {
                val disciplineApi = repo.getDisciplineTitle(disciplineId = disciplineId)

                disciplineDisplay = disciplineApi?.title.toString()
                workTypeId = disciplineApi?.workTypeId!!
            }
            println("Discipline request in ${executeTime}ms")
        }

        viewModelScope.launch {
            val executeTime = measureTimeMillis {
                workTypeDisplay = repo.getWorkTypeTitle(workTypeId = workTypeId).toString()
            }
            println("Work type request in ${executeTime}ms")
        }

        viewModelScope.launch {
            val executeTime = measureTimeMillis {
                studentDisplay = repo.getStudent(studentId = studentId).toString()
            }
            println("Student request in ${executeTime}ms")
        }

    }

    fun registration() {
        viewModelScope.launch {
            val work = if (editable)
                Work(disciplineId = disciplineId, studentId = studentId, title = workTitle)
            else
                Work(disciplineId = disciplineId, studentId = studentId)

            repo.workRegistration(work = work)
        }
    }

}

private fun Int.toBoolean(): Boolean = this == 1