package com.example.diploma.common

import com.example.diploma.common.storage.NetworkConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.run {
            proceed(
                request().newBuilder()
                    .addHeader("Authorization", "Bearer ${NetworkConfig.token}")
                    .build()
            )
        }
    }
}
