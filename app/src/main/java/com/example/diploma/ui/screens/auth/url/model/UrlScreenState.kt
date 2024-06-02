package com.example.diploma.ui.screens.auth.url.model

data class UrlScreenState(
    val isLoading: Boolean,
    val isUrlFormatInvalid: Boolean,
    val isUrlWrong: Boolean,
    val url: String,
    val isNeedToGoNext: Boolean
) {
    companion object {
        val EMPTY: UrlScreenState = UrlScreenState(
            isLoading = false,
            isUrlFormatInvalid = false,
            isUrlWrong = false,
            url = "",
            isNeedToGoNext = false
        )
    }
}
