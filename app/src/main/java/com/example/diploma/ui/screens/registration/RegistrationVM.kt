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
import com.example.diploma.network.NetworkRepo
import com.example.diploma.network.models.filter.Filter
import kotlinx.coroutines.launch

class RegistrationVM(private val repo: NetworkRepo) : ViewModel() {

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

    var employeeList by mutableStateOf<List<Filter>>(emptyList())
        private set

    var selectedEmployee by mutableStateOf(EMPTY_STRING)

    fun fetchData(data: String) {
        if (needDataToGet) {
            val (departmentId, disciplineId, studentId, workTypeId) = data.toListInt()
            this.departmentId = departmentId

            viewModelScope.launch {
                Log.e("Reconnect employee", "For some reason disciplines run over and over again")
                val discipline = repo.getDisciplineTitle(disciplineId = disciplineId)
                this@RegistrationVM.disciplineId = disciplineId

                if (discipline != null) {
                    this@RegistrationVM.discipline = discipline.title
                } else this@RegistrationVM.discipline = "Error"
            }

            viewModelScope.launch {
                val student = repo.getStudent(studentId = studentId)
                this@RegistrationVM.studentId = studentId

                if (student != null) {
                    this@RegistrationVM.student = student
                } else this@RegistrationVM.student = "Error"
            }

            viewModelScope.launch {
                val workType = repo.getWorkType(workTypeId = workTypeId)
                this@RegistrationVM.workTypeId = workTypeId

                if (workType != null) {
                    this@RegistrationVM.needTitle = workType.needTitle
                } else Log.d("RegVM_WorkType_error", "Some api error")
            }

            viewModelScope.launch {
                employeeList = repo.getFilterEmployee()
                Log.e("Reconnect employee", "For some reason its run over and over again")
            }

            needDataToGet = false
        }
    }

    fun registration() {
        viewModelScope.launch {

            if (employeeId == 0) {
                getString(id = R.string.employee_id_error).toToast()
                return@launch
            }

            val map =
                mutableMapOf(
                    "disciplineId" to disciplineId.toString(),
                    "studentId" to studentId.toString(),
                    "workTypeId" to workTypeId.toString(),
                    "departmentId" to departmentId.toString(),
                    "employeeId" to employeeId.toString(),
                )

            if (workTitle != null)
                map += Pair("title", workTitle.toString().trim())

            "title" to workTitle
            repo.workRegistration(map)
        }
    }
}