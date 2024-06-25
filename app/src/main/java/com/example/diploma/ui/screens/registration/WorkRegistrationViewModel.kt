package com.example.diploma.ui.screens.registration

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.R
import com.example.diploma.common.EMPTY_STRING
import com.example.diploma.common.getString
import com.example.diploma.common.toListInt
import com.example.diploma.common.toToast
import com.example.diploma.model.Filter
import com.example.diploma.network.disciplines.DisciplinesRepository
import com.example.diploma.network.students.StudentsRepository
import com.example.diploma.network.works.WorksRepository
import com.example.diploma.network.worktypes.WorkTypesRepository
import kotlinx.coroutines.launch

class WorkRegistrationViewModel(
    private val worksRepository: WorksRepository,
    private val disciplinesRepository: DisciplinesRepository,
    private val studentsRepository: StudentsRepository,
    private val workTypesRepository: WorkTypesRepository
) : ViewModel() {

    private var departmentId by mutableIntStateOf(0)
    private var workTypeId by mutableIntStateOf(0)

    private var needDataToGet = true

    var discipline by mutableStateOf(EMPTY_STRING)
        private set

    private var disciplineId by mutableIntStateOf(0)

    var student by mutableStateOf(EMPTY_STRING)
        private set

    private var studentId by mutableIntStateOf(0)

    var needTitle by mutableStateOf(false)
        private set

    var employeeId by mutableIntStateOf(0)

    var workTitle by mutableStateOf<String?>(null)

    var employeeListDisplayed by mutableStateOf<List<Filter>>(emptyList())
        private set

    private var employeeList by mutableStateOf<List<Filter>>(emptyList())

    var selectedEmployee by mutableStateOf(EMPTY_STRING)

    var regButEnable by mutableStateOf(true)
        private set

    var returnToCamera by mutableStateOf(false)
        private set

    var teacherSearch by mutableStateOf(EMPTY_STRING)
        private set

    fun fetchData(data: String) {
        if (needDataToGet) {
            val (departmentId, disciplineId, studentId, workTypeId) = data.toListInt()
            this.departmentId = departmentId

            viewModelScope.launch {
                Log.e("Reconnect employee", "For some reason disciplines run over and over again")
                val discipline =
                    disciplinesRepository.getDisciplineById(disciplineId = disciplineId)
                this@WorkRegistrationViewModel.disciplineId = disciplineId

                if (discipline != null) {
                    this@WorkRegistrationViewModel.discipline = discipline.title
                } else this@WorkRegistrationViewModel.discipline = "Error"
            }

            viewModelScope.launch {
                val student = studentsRepository.getStudentById(studentId = studentId)
                this@WorkRegistrationViewModel.studentId = studentId

                if (student != null) {
                    this@WorkRegistrationViewModel.student = student.fullName
                } else this@WorkRegistrationViewModel.student = "Error"
            }

            viewModelScope.launch {
                val workType = workTypesRepository.getWorkTypeById(workTypeId = workTypeId)
                this@WorkRegistrationViewModel.workTypeId = workTypeId

                if (workType != null) {
                    this@WorkRegistrationViewModel.needTitle = workType.needTitle
                } else Log.d("RegVM_WorkType_error", "Some api error")
            }

            viewModelScope.launch {
                employeeList = worksRepository.getEmployeesFilters(false) ?: emptyList()
                employeeListDisplayed = employeeList
                Log.e("Reconnect employee", "For some reason its run over and over again")
            }

            needDataToGet = false
        }
    }

    fun filterTeachers(inline: String) {
        teacherSearch = inline

        employeeListDisplayed = employeeList.filter {
            it.title.contains(teacherSearch)
        }

    }

    fun registration() {
        viewModelScope.launch {
            regButEnable = false

            if (employeeId == 0) {
                // TODO: 02/06/2024, Danil Nikolaev: тосты в ui должны выводиться, а не в vm
                getString(id = R.string.teacher_id_error).toToast()
                return@launch
            }

            val newWork = worksRepository.registerNewWork(
                disciplineId = disciplineId,
                studentId = studentId,
                title = workTitle?.trim(),
                workTypeId = workTypeId,
                employeeId = employeeId
            )

            if (newWork != null) {
                returnToCamera = true
            }
        }
    }

    fun clearTeacherSearch() {
        teacherSearch = EMPTY_STRING
        employeeListDisplayed = employeeList
    }
}
