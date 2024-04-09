package com.example.diploma.network

import android.util.Log
import com.example.diploma.common.storage.AccountConfig
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.network.calladapter.NetworkResponse
import com.example.diploma.network.models.filter.Filter
import com.example.diploma.network.models.journal.Journal

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
            is NetworkResponse.ApiError -> TODO()
            is NetworkResponse.NetworkError -> TODO()
            is NetworkResponse.Success -> {
                AccountConfig.department = accountInfo.body.response.departmentId
            }

            is NetworkResponse.UnknownError -> {
                Log.d(TAG, "UnknownError $accountInfo")
            }
        }

    }

}