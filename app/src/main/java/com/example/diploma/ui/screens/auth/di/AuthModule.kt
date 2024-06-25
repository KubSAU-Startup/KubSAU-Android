package com.example.diploma.ui.screens.auth.di

import com.example.diploma.ui.screens.auth.login.LoginViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    viewModelOf(::LoginViewModelImpl)
}
