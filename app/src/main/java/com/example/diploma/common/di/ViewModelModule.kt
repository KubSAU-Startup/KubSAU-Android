package com.example.diploma.common.di

import com.example.diploma.ui.screens.auth.url.UrlViewModel
import com.example.diploma.ui.screens.latestworks.LatestWorksViewModel
import com.example.diploma.ui.screens.registration.WorkRegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

@Deprecated("use separated modules for screens")
val viewModelModule = module {
    viewModelOf(::LatestWorksViewModel)
    viewModelOf(::WorkRegistrationViewModel)
    viewModelOf(::UrlViewModel)
}
