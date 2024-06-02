package com.example.diploma.ui.screens.auth.url

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diploma.common.AppGlobal
import com.example.diploma.common.EMPTY_STRING
import com.example.diploma.common.URL_PATTERN_FORMAT
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.network.NetworkRepo

class UrlVM(private val repo: NetworkRepo) : ViewModel() {

    var url by mutableStateOf(EMPTY_STRING)
        private set

    var showUrlError by mutableStateOf(false)

    private var realUrl = false

    fun updateUrl(url: String) {
        this.url = url.trim()
        showUrlError = false
    }

    fun legitUrl(): Boolean {

        val match = url.matches(Regex(URL_PATTERN_FORMAT))

        if (!match)
            showUrlError = true
        else {
            NetworkConfig.url = url
        }

        return match

//        val match = url.matches(Regex(URL_PATTERN_FORMAT))
//
//        if (!match) {
//            showUrlError = true
//            return match
//        }
//
//        NetworkConfig.url = url
//
//        viewModelScope.launch {
//            val check = repo.checkUrl()
//
//            if (!check) {
//                showUrlError = true
//                NetworkConfig.clearUrl()
//            } else this@UrlVM.realUrl = true
//        }
//
//        return realUrl
    }
}