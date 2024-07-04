package com.kubsau.regrab.ui.screens.auth.di

import com.kubsau.regrab.ui.screens.auth.LoginUseCase
import com.kubsau.regrab.ui.screens.auth.LoginUseCaseImpl
import com.kubsau.regrab.ui.screens.auth.login.LoginViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    viewModelOf(::LoginViewModelImpl)
    singleOf(::LoginUseCaseImpl) bind LoginUseCase::class
}
