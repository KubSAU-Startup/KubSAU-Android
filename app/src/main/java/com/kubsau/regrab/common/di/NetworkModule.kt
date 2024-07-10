package com.kubsau.regrab.common.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kubsau.regrab.common.AuthInterceptor
import com.kubsau.regrab.common.storage.NetworkConfig
import com.kubsau.regrab.network.MoshiConverter
import com.kubsau.regrab.network.ResponseConverterFactory
import com.kubsau.regrab.network.account.AccountRepository
import com.kubsau.regrab.network.account.AccountRepositoryImpl
import com.kubsau.regrab.network.account.AccountService
import com.kubsau.regrab.network.account.AccountUseCase
import com.kubsau.regrab.network.account.AccountUseCaseImpl
import com.kubsau.regrab.network.auth.AuthRepository
import com.kubsau.regrab.network.auth.AuthRepositoryImpl
import com.kubsau.regrab.network.auth.AuthService
import com.kubsau.regrab.network.auth.AuthUseCase
import com.kubsau.regrab.network.auth.AuthUseCaseImpl
import com.kubsau.regrab.network.common.ApiRepository
import com.kubsau.regrab.network.common.ApiRepositoryImpl
import com.kubsau.regrab.network.common.ApiService
import com.kubsau.regrab.network.disciplines.DisciplinesRepository
import com.kubsau.regrab.network.disciplines.DisciplinesRepositoryImpl
import com.kubsau.regrab.network.disciplines.DisciplinesService
import com.kubsau.regrab.network.employees.EmployeeRepository
import com.kubsau.regrab.network.employees.EmployeeRepositoryImpl
import com.kubsau.regrab.network.employees.EmployeeUseCase
import com.kubsau.regrab.network.employees.EmployeeUseCaseImpl
import com.kubsau.regrab.network.employees.EmployeesService
import com.kubsau.regrab.network.students.StudentsRepository
import com.kubsau.regrab.network.students.StudentsRepositoryImpl
import com.kubsau.regrab.network.students.StudentsService
import com.kubsau.regrab.network.works.WorksRepository
import com.kubsau.regrab.network.works.WorksRepositoryImpl
import com.kubsau.regrab.network.works.WorksService
import com.kubsau.regrab.network.worktypes.WorkTypesRepository
import com.kubsau.regrab.network.worktypes.WorkTypesRepositoryImpl
import com.kubsau.regrab.network.worktypes.WorkTypesService
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
    singleOf(::AuthUseCaseImpl) bind AuthUseCase::class
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

    singleOf(::AccountUseCaseImpl) bind AccountUseCase::class
    singleOf(::AccountRepositoryImpl) bind AccountRepository::class
    single { service(AccountService::class.java) }

    singleOf(::EmployeeUseCaseImpl) bind EmployeeUseCase::class
    singleOf(::EmployeeRepositoryImpl) bind EmployeeRepository::class
    single { service(EmployeesService::class.java) }
}

private fun <T> Scope.service(className: Class<T>): T = get<Retrofit>().create(className)
