package com.kubsau.regrab.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubsau.regrab.BuildConfig
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

fun <Success : ResponseModel<*>, SuccessDomain : Any, ErrorDomain : Any>
        ApiResult<Success, ErrorModel>.mapResult(
    successMapper: (Success) -> SuccessDomain,
    errorMapper: (ErrorModel?) -> ErrorDomain?
): ApiResult<SuccessDomain, ErrorDomain> {
    if (BuildConfig.DEBUG) printStackTraceIfAny()

    return when (this) {
        is ApiResult.Success -> {
            if (value.isSuccessful) {
                ApiResult.success(successMapper(value))
            } else {
                ApiResult.apiFailure(errorMapper(value.error))
            }
        }

        is ApiResult.Failure.NetworkFailure -> ApiResult.networkFailure(error)
        is ApiResult.Failure.UnknownFailure -> ApiResult.unknownFailure(error)
        is ApiResult.Failure.HttpFailure -> ApiResult.httpFailure(code, errorMapper(error))
        is ApiResult.Failure.ApiFailure -> ApiResult.apiFailure(errorMapper(error))
    }
}

fun <Success : Any, Error : Any> ApiResult<Success, Error>.printStackTraceIfAny() {
    val throwable = when (this) {
        is ApiResult.Failure.NetworkFailure -> error
        is ApiResult.Failure.UnknownFailure -> error
        else -> null
    }
    throwable?.printStackTrace()
}

context(ViewModel)
fun <T> Flow<T>.listenValue(action: suspend (T) -> Unit) = listenValue(viewModelScope, action)

suspend fun <T> Flow<T>.listenValue(action: suspend (T) -> Unit) =
    coroutineScope { listenValue(this, action) }

fun <T> Flow<T>.listenValue(
    coroutineScope: CoroutineScope,
    action: suspend (T) -> Unit
): Job = onEach(action::invoke).launchIn(coroutineScope)

context(ViewModel)
fun <T> MutableStateFlow<T>.setValue(function: (T) -> T) {
    val newValue = function(value)
    update { newValue }
}
