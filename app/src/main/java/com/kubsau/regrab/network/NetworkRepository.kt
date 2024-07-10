
package com.kubsau.regrab.network
/*
import android.util.Log
import com.kubsau.regrab.common.intToString
import com.kubsau.regrab.common.storage.AccountConfig
import com.kubsau.regrab.common.storage.NetworkConfig
import com.kubsau.regrab.common.toToast
import com.kubsau.regrab.network.calladapter.NetworkResponse
import com.kubsau.regrab.network.common.ApiService
import com.kubsau.regrab.model.Discipline
import com.kubsau.regrab.model.GetLatestWorksResponse
import com.kubsau.regrab.model.WorkType
import com.slack.eithernet.ApiResult

class NetworkRepository(private val api: ApiService) {

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

    suspend fun getLatestWorks(filters: Map<String, Int>, offset: Int): GetLatestWorksResponse? {
        return when (val response = api.journals(filters, offset)) {
//            is NetworkResponse.ApiError -> {
//                Log.d(TAG, "Error ${response.body.=}")
//                GetLatestWorksResponse()
//            }
//
//            is NetworkResponse.NetworkError -> {
//                Log.d(TAG, "NetworkError")
//                GetLatestWorksResponse()
//            }
//
//            is NetworkResponse.Success -> {
//                Log.d(TAG, "Success ${response.body}")
//                response.body.response
//            }
//
//            is NetworkResponse.UnknownError -> {
//                Log.d(TAG, "UnknownError ${response.error}")
//                GetLatestWorksResponse()
//            }

            is ApiResult.Success -> {
                response.value.requireResponse()
            }

            is ApiResult.Failure.ApiFailure -> {
                null
            }

            is ApiResult.Failure.HttpFailure -> {
                null
            }

            is ApiResult.Failure.NetworkFailure -> {
                null
            }

            is ApiResult.Failure.UnknownFailure -> {
                null
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
        map: Map<String, String>
    ): Boolean {
        return when (val x = api.workRegistration(map)) {
            is NetworkResponse.ApiError -> {
                Log.e("RegRequest", "Api error $x")
                "Api error $x".toToast()
                false
            }

            is NetworkResponse.NetworkError -> {
                "Network error $x".toToast()
                Log.e("RegRequest", "Network error $x")
                false
            }

            is NetworkResponse.Success -> {
                if (x.body.success) {
                    "Success reg".toToast()
                    true
                } else {
                    "Reg error ${x.body.error?.errorMessage}".toToast()
                    Log.e("RegRequest", "Reg error ${x.body.error?.errorMessage}")
                    false
                }
            }

            is NetworkResponse.UnknownError -> {
                "Unknown error $x".toToast()
                Log.e("RegRequest", "Unknown error $x")
                false
            }
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


    companion object {
        private const val TAG = "CallAdapter"
    }
}
*/
