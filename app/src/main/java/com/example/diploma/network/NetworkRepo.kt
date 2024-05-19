package com.example.diploma.network

import android.util.Log
import com.example.diploma.common.intToString
import com.example.diploma.common.storage.AccountConfig
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.network.calladapter.NetworkResponse
import com.example.diploma.network.models.discipline.Discipline
import com.example.diploma.network.models.journal.Journal
import com.example.diploma.network.models.worktype.WorkType

class NetworkRepo(private val api: Api) {

    private val TAG = "CallAdapter"
    suspend fun auth(queryParams: Map<String, String>): Boolean {
        return when (val response = api.auth(queryParams)) {
            is NetworkResponse.ApiError -> {
                Log.d(TAG, "Error ${response.body.error}")
                false
            }

            is NetworkResponse.NetworkError -> {
                Log.d(TAG, "NetworkError")
                false
            }

            is NetworkResponse.Success -> {
                if (response.body.success) {
                    Log.d(TAG, "Success ${response.body}")
                    NetworkConfig.token = response.body.response.token
                    AccountConfig.departmentList =
                        response.body.response.departmentIds.intToString()
                    true
                } else {
                    Log.d(TAG, "Fail ${response.body}")
                    false
                }

            }

            is NetworkResponse.UnknownError -> {
                Log.d(TAG, "UnknownError ${response.error}")
                false
            }
        }
    }

    suspend fun getJournal(filters: Map<String, Int>): Journal {
        return when (val response = api.journals(filters)) {
            is NetworkResponse.ApiError -> {
                Log.d(TAG, "Error ${response.body.error}")
                Journal()
            }

            is NetworkResponse.NetworkError -> {
                Log.d(TAG, "NetworkError")
                Journal()
            }

            is NetworkResponse.Success -> {
                Log.d(TAG, "Success ${response.body}")
                response.body.response
            }

            is NetworkResponse.UnknownError -> {
                Log.d(TAG, "UnknownError ${response.error}")
                Journal()
            }
        }
    }

    suspend fun getDisciplineTitle(disciplineId: Int): Discipline? {
        return when (val discipline = api.discipline(disciplineId = disciplineId)) {
            is NetworkResponse.ApiError -> {
                Log.e(TAG, "Some API error ${discipline.body.error}")
                null
            }

            is NetworkResponse.NetworkError -> {
                Log.e(TAG, "Some network error ${discipline.error}")
                null
            }

            is NetworkResponse.Success -> {
                discipline.body.response
            }

            is NetworkResponse.UnknownError -> {
                Log.e(TAG, "Some unknown error ${discipline.error}")
                null
            }
        }
    }

    suspend fun getWorkType(workTypeId: Int): WorkType? {
        return when (val workTypes = api.workTypes(workTypeId = workTypeId)) {
            is NetworkResponse.ApiError -> {

                Log.e(TAG, "Some API error ${workTypes.body.error}")
                null
            }

            is NetworkResponse.NetworkError -> {
                Log.e(TAG, "Some API error ${workTypes.error}")
                null
            }

            is NetworkResponse.Success -> {
                workTypes.body.response
            }

            is NetworkResponse.UnknownError -> {
                Log.e(TAG, "Some API error ${workTypes.error}")
                null
            }
        }
    }

    suspend fun getStudent(studentId: Int): String? {
        return when (val student = api.student(studentID = studentId)) {
            is NetworkResponse.ApiError -> {
                Log.e(TAG, "Some API error ${student.body.error}")
                null
            }

            is NetworkResponse.NetworkError -> {
                Log.e(TAG, "Some API error ${student.error}")
                null
            }

            is NetworkResponse.Success -> {
                with(student.body.response.student) {
                    "$lastName $firstName $middleName"
                }
            }

            is NetworkResponse.UnknownError -> {
                Log.e(TAG, "Some API error ${student.error}")
                null
            }
        }
    }

    suspend fun workRegistration(
        disciplineId: Int,
        studentId: Int,
        title: String?,
        workTypeId: Int,
        departmentId: Int,
        employeeId: Int
    ) {
        when (val x = api.workRegistration(
            disciplineId = disciplineId,
            studentId = studentId,
            title = title,
            workTypeId = workTypeId,
            departmentId = departmentId,
            employeeId = employeeId
        )) {
            is NetworkResponse.ApiError -> Log.d(TAG, x.toString())
            is NetworkResponse.NetworkError -> Log.d(TAG, x.toString())
            is NetworkResponse.Success -> Log.d(TAG, x.toString())
            is NetworkResponse.UnknownError -> Log.d(TAG, x.toString())
        }
    }

    suspend fun getFilterDiscipline() = when (val response = api.filterDisciplines()) {
        is NetworkResponse.ApiError -> {
            Log.d(TAG, "Api error ${response.body}")
            emptyList()
        }

        is NetworkResponse.NetworkError -> {
            Log.d(TAG, "Api error ${response.error}")
            emptyList()
        }

        is NetworkResponse.Success -> response.body.response
        is NetworkResponse.UnknownError -> {
            Log.d(TAG, "Unknown error ${response.error}")
            emptyList()
        }
    }

    suspend fun getFilterWorkType() = when (val response = api.filterWorkTypes()) {
        is NetworkResponse.ApiError -> {
            Log.d(TAG, "Api error ${response.body}")
            emptyList()
        }

        is NetworkResponse.NetworkError -> {
            Log.d(TAG, "Api error ${response.error}")
            emptyList()
        }

        is NetworkResponse.Success -> response.body.response
        is NetworkResponse.UnknownError -> {
            Log.d(TAG, "Unknown error ${response.error}")
            emptyList()
        }
    }

    suspend fun getFilterEmployee() = when (val response = api.filterEmployees()) {
        is NetworkResponse.ApiError -> {
            Log.d(TAG, "Api error ${response.body}")
            emptyList()
        }

        is NetworkResponse.NetworkError -> {
            Log.d(TAG, "Api error ${response.error}")
            emptyList()
        }

        is NetworkResponse.Success -> response.body.response
        is NetworkResponse.UnknownError -> {
            Log.d(TAG, "Unknown error ${response.error}")
            emptyList()
        }
    }

    suspend fun getFilterGroup() = when (val response = api.filterGroups()) {
        is NetworkResponse.ApiError -> {
            Log.d(TAG, "Api error ${response.body}")
            emptyList()
        }

        is NetworkResponse.NetworkError -> {
            Log.d(TAG, "Api error ${response.error}")
            emptyList()
        }

        is NetworkResponse.Success -> response.body.response
        is NetworkResponse.UnknownError -> {
            Log.d(TAG, "Unknown error ${response.error}")
            emptyList()
        }
    }

}