package com.example.diploma.common

import com.example.diploma.network.ApiResponse
import com.slack.eithernet.ApiResult
import com.slack.eithernet.fold

fun <T, R : ApiResponse<T>, E : Any> ApiResult<R, E>.foldOnSuccess(): T? = fold(
    onSuccess = { response ->
        if (response.isSuccessful) {
            response.requireResponse()
        } else null
    },
    onFailure = { null }
)
