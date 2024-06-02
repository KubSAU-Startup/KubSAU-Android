package com.example.diploma.ui.screens.auth.url

import android.util.Log
import android.webkit.URLUtil
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.network.Api
import com.example.diploma.network.calladapter.NetworkResponseAdapterFactory
import com.example.diploma.ui.screens.auth.url.model.UrlScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class UrlVM : ViewModel() {

    var screenState by mutableStateOf(UrlScreenState.EMPTY)
        private set

    fun onTextInputChanged(newText: String) {
        screenState = screenState.copy(
            url = newText.trim(),
            isUrlFormatInvalid = false,
            isUrlWrong = false
        )
    }

    fun onNextButtonClick() {
        if (!URLUtil.isValidUrl(screenState.url)) {
            screenState = screenState.copy(isUrlFormatInvalid = true)
        } else {
            checkBackendVersion()
        }
    }

    private fun checkBackendVersion() {
        screenState = screenState.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                buildRetrofitApi(screenState.url).getBackendInfo()
            }.fold(
                onSuccess = { info ->
                    Log.d("UrlVM", "checkBackendVersion: backendInfo: $info")
                    NetworkConfig.url = screenState.url
                    screenState = screenState.copy(isLoading = false, isNeedToGoNext = true)
                },
                onFailure = {
                    screenState = screenState.copy(isLoading = false, isUrlWrong = true)
                }
            )
        }
    }

    private fun buildRetrofitApi(url: String): Api {
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .build().create(Api::class.java)
    }

    fun onWentNext() {
        screenState = screenState.copy(isNeedToGoNext = false)
    }
}
