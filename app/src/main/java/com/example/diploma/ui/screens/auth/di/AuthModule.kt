package com.example.diploma.ui.screens.auth.di

import com.example.diploma.network.auth.AuthService
import com.example.diploma.ui.screens.auth.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    viewModelOf(::LoginViewModel)
}
