package com.example.diploma.common.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.diploma.common.AuthInterceptor
import com.example.diploma.common.storage.NetworkConfig
import com.example.diploma.network.MoshiConverter
import com.example.diploma.network.ResponseConverterFactory
import com.example.diploma.network.account.AccountRepository
import com.example.diploma.network.account.AccountRepositoryImpl
import com.example.diploma.network.account.AccountService
import com.example.diploma.network.auth.AuthRepository
import com.example.diploma.network.auth.AuthRepositoryImpl
import com.example.diploma.network.auth.AuthService
import com.example.diploma.network.common.ApiRepository
import com.example.diploma.network.common.ApiRepositoryImpl
import com.example.diploma.network.common.ApiService
import com.example.diploma.network.disciplines.DisciplinesRepository
import com.example.diploma.network.disciplines.DisciplinesRepositoryImpl
import com.example.diploma.network.disciplines.DisciplinesService
import com.example.diploma.network.employees.EmployeeRepository
import com.example.diploma.network.employees.EmployeeRepositoryImpl
import com.example.diploma.network.employees.EmployeesService
import com.example.diploma.network.students.StudentsRepository
import com.example.diploma.network.students.StudentsRepositoryImpl
import com.example.diploma.network.students.StudentsService
import com.example.diploma.network.works.WorksRepository
import com.example.diploma.network.works.WorksRepositoryImpl
import com.example.diploma.network.works.WorksService
import com.example.diploma.network.worktypes.WorkTypesRepository
import com.example.diploma.network.worktypes.WorkTypesRepositoryImpl
import com.example.diploma.network.worktypes.WorkTypesService
import com.slack.eithernet.ApiResultCallAdapterFactory
import com.slack.eithernet.ApiResultConverterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Deprecated("use separated modules for repo's and services")
val networkModule = module {
    single { Moshi.Builder().build() }
    singleOf(::MoshiConverter)
    single { ChuckerCollector(get()) }
    single { ChuckerInterceptor.Builder(get()).collector(get()).build() }
    singleOf(::AuthInterceptor)
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(get<ChuckerInterceptor>())
            .followRedirects(true)
            .followSslRedirects(true)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.url)
            .addConverterFactory(ApiResultConverterFactory)
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addConverterFactory(ResponseConverterFactory(get<MoshiConverter>()))
            .client(get())
            .build()
    }

    // TODO: 21/06/2024, Danil Nikolaev: use separated modules
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
    single { service(AuthService::class.java) }

    singleOf(::ApiRepositoryImpl) bind ApiRepository::class
    single { service(ApiService::class.java) }

    singleOf(::DisciplinesRepositoryImpl) bind DisciplinesRepository::class
    single { service(DisciplinesService::class.java) }

    singleOf(::StudentsRepositoryImpl) bind StudentsRepository::class
    single { service(StudentsService::class.java) }

    singleOf(::WorksRepositoryImpl) bind WorksRepository::class
    single { service(WorksService::class.java) }

    singleOf(::WorkTypesRepositoryImpl) bind WorkTypesRepository::class
    single { service(WorkTypesService::class.java) }

    singleOf(::AccountRepositoryImpl) bind AccountRepository::class
    single { service(AccountService::class.java) }

    singleOf(::EmployeeRepositoryImpl) bind EmployeeRepository::class
    single { service(EmployeesService::class.java) }
}

private fun <T> Scope.service(className: Class<T>): T = get<Retrofit>().create(className)
