package com.example.diploma.ui.screens.registration

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.common.navigation.graphs.QR_KEY
import com.example.diploma.common.toListInt
import com.example.diploma.model.Filter
import com.example.diploma.network.disciplines.DisciplinesRepository
import com.example.diploma.network.students.StudentsRepository
import com.example.diploma.network.works.WorksRepository
import com.example.diploma.network.worktypes.WorkTypesRepository
import com.example.diploma.ui.screens.registration.model.WorkRegisterScreenState
import com.example.diploma.ui.screens.registration.model.toTeacher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface WorkRegisterViewModel {

    val screenState: StateFlow<WorkRegisterScreenState>

    fun onWorkTitleInputChanged(newTitle: String)
    fun onTeacherQueryInputChanged(newQuery: String)
    fun onTeacherClearFilterButtonClicked()
    fun onTeacherClicked(teacherId: Int)

    fun onRegisterButtonClicked()

    fun onCameraOpened()

    fun errorToastShown()
}

class WorkRegisterViewModelImpl(
    private val worksRepository: WorksRepository,
    private val disciplinesRepository: DisciplinesRepository,
    private val studentsRepository: StudentsRepository,
    private val workTypesRepository: WorkTypesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), WorkRegisterViewModel {

    override val screenState = MutableStateFlow(WorkRegisterScreenState.EMPTY)

    init {
        val qrCodeValue: String? = savedStateHandle[QR_KEY]
        fetchData(qrCodeValue)
    }

    private fun fetchData(qrCodeValue: String?) {
        if (qrCodeValue == null) {
            throw IllegalStateException("QR code value cannot be null")
        }

        val (departmentId, disciplineId, studentId, workTypeId) = qrCodeValue.toListInt()

        viewModelScope.launch(Dispatchers.IO) {
            var newState = screenState.value.copy(
                isLoading = true,
                departmentId = departmentId,
                disciplineId = disciplineId,
                studentId = studentId,
                workTypeId = workTypeId
            )
            screenState.update { newState }

            val discipline = async { disciplinesRepository.getDisciplineById(disciplineId) }.await()
            val student = async { studentsRepository.getStudentById(studentId) }.await()
            val workType = async { workTypesRepository.getWorkTypeById(workTypeId) }.await()
            val teachers = async { worksRepository.getEmployeesFilters(false) }.await()

            val teacherItems = teachers?.map(Filter::toTeacher) ?: emptyList()

            newState = screenState.value.copy(
                isLoading = false,
                discipline = discipline?.title.orEmpty(),
                student = student?.fullName.orEmpty(),
                needTitle = workType?.needTitle ?: false,
                teachers = teacherItems,
                filteredTeachers = teacherItems
            )
            screenState.update { newState }
        }
    }

    override fun onWorkTitleInputChanged(newTitle: String) {
        val newState = screenState.value.copy(
            workTitle = newTitle
        )
        screenState.update { newState }
    }

    override fun onTeacherQueryInputChanged(newQuery: String) {
        val newState = screenState.value.copy(
            teacherQuery = newQuery,
            filteredTeachers = if (newQuery.trim().isEmpty()) {
                screenState.value.teachers
            } else {
                screenState.value.teachers.filter { teacher ->
                    teacher.title.lowercase().contains(newQuery.lowercase())
                }
            }
        )
        screenState.update { newState }
    }

    override fun onTeacherClearFilterButtonClicked() {
        onTeacherQueryInputChanged("")
    }

    override fun onTeacherClicked(teacherId: Int) {
        val newState = screenState.value.copy(selectedTeacherId = teacherId)
        screenState.update { newState }
    }

    override fun onRegisterButtonClicked() {
        val disciplineId = screenState.value.disciplineId
        val studentId = screenState.value.studentId
        val workTypeId = screenState.value.workTypeId
        val teacherId = screenState.value.selectedTeacherId

        if (teacherId == null) {
            val newState = screenState.value.copy(showPickTeacherToast = true)
            screenState.update { newState }
            return
        }

        val workTitle = screenState.value.workTitle

        viewModelScope.launch(Dispatchers.IO) {
            var newState = screenState.value.copy(isLoading = true)
            screenState.update { newState }

            val registeredWork = worksRepository.registerNewWork(
                disciplineId = disciplineId,
                studentId = studentId,
                title = workTitle?.trim(),
                workTypeId = workTypeId,
                employeeId = teacherId
            )

            if (registeredWork == null) {
                // TODO: 25/06/2024, Danil Nikolaev: show error
            } else {
                newState = screenState.value.copy(
                    isLoading = false,
                    isNeedOpenCamera = true
                )
                screenState.update { newState }
            }
        }
    }

    override fun onCameraOpened() {
        val newState = screenState.value.copy(
            isNeedOpenCamera = false
        )
        screenState.update { newState }
    }

    override fun errorToastShown() {
        val newState = screenState.value.copy(
            showPickTeacherToast = false
        )
        screenState.update { newState }
    }
}
