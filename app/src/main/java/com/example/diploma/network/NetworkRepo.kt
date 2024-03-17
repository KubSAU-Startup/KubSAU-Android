package com.example.diploma.network

import android.util.Log
import com.example.diploma.common.NetworkConfig
import com.example.diploma.network.calladapter.NetworkResponse
import com.example.diploma.network.models.filter.Filter
import com.example.diploma.network.models.journal.Journal

class NetworkRepo(private val api: Api) {
    suspend fun auth(queryParams: Map<String, String>): Boolean {
        return when (val response = api.auth(queryParams)) {
            is NetworkResponse.ApiError -> {
                Log.d("CallAdapter", "Error ${response.body.error}")
                false
            }

            is NetworkResponse.NetworkError -> {
                Log.d("CallAdapter", "NetworkError")
                false
            }

            is NetworkResponse.Success -> {
                Log.d("CallAdapter", "Success ${response.body}")
                NetworkConfig.token = response.body.response.token
                true
            }

            is NetworkResponse.UnknownError -> {
                Log.d("CallAdapter", "UnknownError ${response.error}")
                false
            }
        }
    }

    suspend fun getJournal(filters: Map<String, Int>) =
        when (val response = api.journals(filters)) {
            is NetworkResponse.ApiError -> {
                Log.d("CallAdapter", "Error ${response.body.error}")
                Journal()
            }

            is NetworkResponse.NetworkError -> {
                Log.d("CallAdapter", "NetworkError")
                Journal()
            }

            is NetworkResponse.Success -> {
                Log.d("CallAdapter", "Success ${response.body}")
                response.body.response
            }

            is NetworkResponse.UnknownError -> {
                Log.d("CallAdapter", "UnknownError ${response.error}")
                Journal()
            }
        }

    suspend fun getFilters(): Filter {
        return when (val response = api.filters()) {
            is NetworkResponse.ApiError -> {
                Log.d("CallAdapter", "Error ${response.body.error}")
                Filter()
            }

            is NetworkResponse.NetworkError -> {
                Log.d("CallAdapter", "NetworkError")
                Filter()
            }

            is NetworkResponse.Success -> {
                Log.d("CallAdapter", "Success ${response.body}")
                response.body.response
            }

            is NetworkResponse.UnknownError -> {
                Log.d("CallAdapter", "UnknownError ${response.error}")
                Filter()
            }
        }
    }
}