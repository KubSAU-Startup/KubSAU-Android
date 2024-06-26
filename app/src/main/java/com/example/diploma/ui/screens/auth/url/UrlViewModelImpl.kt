package com.example.diploma.ui.screens.auth.url

import android.webkit.URLUtil
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.network.calladapter.NetworkResponseAdapterFactory
import com.example.diploma.network.common.ApiService
import com.example.diploma.ui.screens.auth.url.model.UrlScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface UrlViewModel {

    val screenState: StateFlow<UrlScreenState>

    fun onTextInputChanged(newText: String)
    fun onNextButtonClicked()

    fun onWentNext()
    fun consumeRestart()
}

class UrlViewModelImpl(savedStateHandle: SavedStateHandle) : ViewModel(), UrlViewModel {

    override val screenState = MutableStateFlow(
        UrlScreenState.EMPTY.copy(url = NetworkConfig.url)
    )

    init {
        val prevRoute: String? = savedStateHandle["prevRoute"]
        val newState = screenState.value.copy(showBackButton = prevRoute != null)
        screenState.update { newState }
    }

    override fun onTextInputChanged(newText: String) {
        val newState = screenState.value.copy(
            url = newText.trim(),
            isUrlFormatInvalid = false,
            isUrlWrong = false
        )
        screenState.update { newState }
    }

    override fun onNextButtonClicked() {
        if (!URLUtil.isValidUrl(screenState.value.url)) {
            val newState = screenState.value.copy(isUrlFormatInvalid = true)
            screenState.update { newState }
        } else {
            checkBackendVersion()
        }
    }

    private fun checkBackendVersion() {
        var newState = screenState.value.copy(isLoading = true)
        screenState.update { newState }

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                buildRetrofitApi(screenState.value.url).getBackendInfo()
            }.fold(
                onSuccess = {
                    newState = screenState.value.copy(isLoading = false)

                    if (NetworkConfig.url != screenState.value.url) {
                        NetworkConfig.url = screenState.value.url
                        newState = newState.copy(restart = true)
                    } else {
                        newState = newState.copy(isNeedToGoNext = true)
                    }

                    screenState.update { newState }
                },
                onFailure = {
                    newState = screenState.value.copy(isLoading = false, isUrlWrong = true)
                    screenState.update { newState }
                }
            )
        }
    }

    private fun buildRetrofitApi(url: String): ApiService {
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .build().create(ApiService::class.java)
    }

    override fun onWentNext() {
        val newState = screenState.value.copy(isNeedToGoNext = false)
        screenState.update { newState }
    }

    override fun consumeRestart() {
        val newState = screenState.value.copy(restart = false)
        screenState.update { newState }
    }
}
