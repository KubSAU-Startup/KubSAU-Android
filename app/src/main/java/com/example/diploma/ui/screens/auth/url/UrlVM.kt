package com.example.diploma.ui.screens.auth.url

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diploma.common.EMPTY_STRING
import com.example.diploma.common.URL_PATTERN_FORMAT

class UrlVM : ViewModel() {

    var url by mutableStateOf(EMPTY_STRING)
        private set

    var showUrlError by mutableStateOf(false)

    fun updateUrl(url: String) {
        this.url = url
        showUrlError = false
    }

    fun legitUrl(): Boolean {
        val match = url.matches(Regex(URL_PATTERN_FORMAT))

        if (!match)
            showUrlError = true

        return match
    }
}