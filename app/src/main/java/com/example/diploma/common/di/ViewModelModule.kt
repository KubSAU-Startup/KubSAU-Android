package com.example.diploma.common.di

import com.example.diploma.ui.screens.auth.url.UrlViewModelImpl
import com.example.diploma.ui.screens.latestworks.LatestWorksViewModelImpl
import com.example.diploma.ui.screens.profile.ProfileViewModelImpl
import com.example.diploma.ui.screens.registration.WorkRegisterViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

@Deprecated("use separated modules for screens")
val viewModelModule = module {
    viewModelOf(::LatestWorksViewModelImpl)
    viewModelOf(::WorkRegisterViewModelImpl)
    viewModelOf(::UrlViewModelImpl)

    viewModelOf(::ProfileViewModelImpl)
}
