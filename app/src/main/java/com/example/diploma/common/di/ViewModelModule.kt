package com.example.diploma.common.di

import com.example.diploma.ui.screens.camera.CameraVM
import com.example.diploma.ui.screens.journal.JournalVM
import com.example.diploma.ui.screens.login.LoginVM
import com.example.diploma.ui.screens.test.one.TestVM
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::TestVM)
    viewModelOf(::LoginVM)
    viewModelOf(::CameraVM)
    viewModelOf(::JournalVM)
}