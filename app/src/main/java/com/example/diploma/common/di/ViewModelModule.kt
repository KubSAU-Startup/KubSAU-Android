package com.example.diploma.common.di

import com.example.diploma.ui.screens.journal.JournalVM
import com.example.diploma.ui.screens.login.LoginVM
import com.example.diploma.ui.screens.registration.RegistrationVM
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginVM)
    viewModelOf(::JournalVM)
    viewModelOf(::RegistrationVM)
}