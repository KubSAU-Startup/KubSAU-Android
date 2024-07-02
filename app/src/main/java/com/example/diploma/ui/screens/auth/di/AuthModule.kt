package com.example.diploma.ui.screens.auth.di

import com.example.diploma.ui.screens.auth.LoginUseCase
import com.example.diploma.ui.screens.auth.LoginUseCaseImpl
import com.example.diploma.ui.screens.auth.login.LoginViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    viewModelOf(::LoginViewModelImpl)
    singleOf(::LoginUseCaseImpl) bind LoginUseCase::class
}
