package com.example.diploma.network

const val CODE_UNKNOWN_ERROR = 1
const val CODE_BAD_REQUEST = 3
const val CODE_ACCESS_DENIED = 4
const val CODE_WRONG_TOKEN_FORMAT = 5
const val CODE_UNKNOWN_TOKEN_ERROR = 6

const val CODE_WRONG_CREDENTIALS = 101

const val CODE_CONTENT_NOT_FOUND = 1001

sealed class ErrorDomain(val code: Int, val description: String) {

    companion object {
        fun parse(code: Int, message: String? = null): ErrorDomain = when (code) {
            CODE_UNKNOWN_ERROR -> UnknownError
            CODE_BAD_REQUEST -> BadRequest(message.orEmpty())
            CODE_ACCESS_DENIED -> AccessDenied(message.orEmpty())
            CODE_WRONG_TOKEN_FORMAT -> WrongTokenFormat
            CODE_UNKNOWN_TOKEN_ERROR -> UnknownTokenError

            CODE_WRONG_CREDENTIALS -> WrongCredentials

            CODE_CONTENT_NOT_FOUND -> ContentNotFound

            else -> throw IllegalArgumentException("Unknown error with code: $code")
        }
    }

    data object WrongUrlError : ErrorDomain(10001, "Wrong url")

    data object UnknownError : ErrorDomain(CODE_UNKNOWN_ERROR, "Unknown error")
    data class BadRequest(val message: String) : ErrorDomain(CODE_BAD_REQUEST, "Bad request")
    data class AccessDenied(val message: String) : ErrorDomain(CODE_ACCESS_DENIED, "Access denied")
    data object WrongTokenFormat : ErrorDomain(CODE_WRONG_TOKEN_FORMAT, "Wrong token format")
    data object UnknownTokenError : ErrorDomain(CODE_UNKNOWN_TOKEN_ERROR, "Unknown token error")

    data object WrongCredentials : ErrorDomain(CODE_WRONG_CREDENTIALS, "Wrong credentials")

    data object ContentNotFound : ErrorDomain(CODE_CONTENT_NOT_FOUND, "Content not found")
}
