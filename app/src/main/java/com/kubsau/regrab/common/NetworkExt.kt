package com.kubsau.regrab.common

import com.kubsau.regrab.network.ErrorDomain
import com.kubsau.regrab.network.ErrorModel
import com.kubsau.regrab.network.ResponseModel
import com.kubsau.regrab.network.State
import com.kubsau.regrab.network.mapResult
import com.kubsau.regrab.network.toStateApiError
import com.slack.eithernet.ApiResult
import com.slack.eithernet.fold

fun <T, R : ResponseModel<T>, E : Any> ApiResult<R, E>.foldOnSuccess(): T? = fold(
    onSuccess = { response ->
        if (response.isSuccessful) {
            response.requireResponse()
        } else null
    },
    onFailure = { null }
)

fun <T : Any, R : ResponseModel<T>> ApiResult<R, ErrorModel>.mapDefault(): ApiResult<T, ErrorDomain> =
    mapResult(
        successMapper = { response -> response.requireResponse() },
        errorMapper = { error -> error?.toDomain() }
    )

fun <T : Any> ApiResult<T, ErrorDomain>.mapToState() = when (this) {
    is ApiResult.Success -> State.Success(this.value)

    is ApiResult.Failure.NetworkFailure -> State.Error.ConnectionError
    is ApiResult.Failure.UnknownFailure -> State.UNKNOWN_ERROR
    is ApiResult.Failure.HttpFailure -> this.error.toStateApiError()
    is ApiResult.Failure.ApiFailure -> this.error.toStateApiError()
}
