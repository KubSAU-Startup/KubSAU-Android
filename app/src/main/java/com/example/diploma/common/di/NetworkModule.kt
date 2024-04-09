package com.example.diploma.common.di

import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.network.Api
import com.example.diploma.network.NetworkRepo
import com.example.diploma.network.calladapter.NetworkResponseAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://testbackend.melod1n.dedyn.io/"

val networkModule = module {
    single {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .addInterceptor(
                        Interceptor { chain: Interceptor.Chain ->
                            chain.run {
                                proceed(
                                    request().newBuilder()
                                        .addHeader("Authorization", "Bearer ${NetworkConfig.token}")
                                        .build()
                                )
                            }
                        }
                    )
                    .build()
            )
            .build().create(Api::class.java)
    }

    single {
        NetworkRepo(get())
    }
}
