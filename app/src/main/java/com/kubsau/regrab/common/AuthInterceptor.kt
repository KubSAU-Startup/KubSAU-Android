package com.kubsau.regrab.common

import com.kubsau.regrab.common.storage.NetworkConfig
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
