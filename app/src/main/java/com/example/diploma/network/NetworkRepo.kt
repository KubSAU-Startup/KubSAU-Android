package com.example.diploma.network

import android.util.Log
import com.example.diploma.common.storage.AccountConfig
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.network.calladapter.NetworkResponse
import com.example.diploma.network.models.discipline.Discipline
import com.example.diploma.network.models.filter.Filter
import com.example.diploma.network.models.journal.Journal
import com.example.diploma.network.models.work.Work

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
                Log.d(TAG, "Success ${response.body}")
                NetworkConfig.token = response.body.response.token
                getAccountInfo()
                true
            }

            is NetworkResponse.UnknownError -> {
                Log.d(TAG, "UnknownError ${response.error}")
                false
            }
        }
    }

    suspend fun getJournal(filters: Map<String, Int>) =
        when (val response = api.journals(filters)) {
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

    suspend fun getFilters(): Filter {
        return when (val response = api.filters()) {
            is NetworkResponse.ApiError -> {
                Log.d(TAG, "Error ${response.body.error}")
                Filter()
            }

            is NetworkResponse.NetworkError -> {
                Log.d(TAG, "NetworkError")
                Filter()
            }

            is NetworkResponse.Success -> {
                Log.d(TAG, "Success ${response.body}")
                response.body.response
            }

            is NetworkResponse.UnknownError -> {
                Log.d(TAG, "UnknownError ${response.error}")
                Filter()
            }
        }
    }

    private suspend fun getAccountInfo() {
        when (val accountInfo = api.accountInfo()) {
            is NetworkResponse.ApiError -> {
                Log.e(TAG, "Some API error\n${accountInfo}")
            }

            is NetworkResponse.NetworkError -> {
                Log.e(TAG, "Some network error\n${accountInfo.error}")
            }

            is NetworkResponse.Success -> {
                AccountConfig.department = accountInfo.body.response.departmentId
            }

            is NetworkResponse.UnknownError -> {
                Log.d(TAG, "UnknownError ${accountInfo.error}")
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

    suspend fun getWorkTypeTitle(workTypeId: Int): String? {
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
                workTypes.body.response.title
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
                with(student.body.response) {
                    "$lastName $firstName $middleName"
                }
            }

            is NetworkResponse.UnknownError -> {
                Log.e(TAG, "Some API error ${student.error}")
                null
            }
        }
    }

    suspend fun workRegistration(work: Work) {
        when (val x = api.workRegistration(
            title = work.title,
            studentId = work.studentId,
            disciplineId = work.disciplineId,
            registrationDate = work.registrationDate
        )) {
            is NetworkResponse.ApiError -> Log.d(TAG, x.toString())
            is NetworkResponse.NetworkError -> Log.d(TAG, x.toString())
            is NetworkResponse.Success -> Log.d(TAG, x.toString())
            is NetworkResponse.UnknownError -> Log.d(TAG, x.toString())
        }
    }

}