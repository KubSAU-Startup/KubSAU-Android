package com.kubsau.regrab.common.di

import com.kubsau.regrab.ui.screens.auth.url.UrlViewModelImpl
import com.kubsau.regrab.ui.screens.latestworks.LatestWorksViewModelImpl
import com.kubsau.regrab.ui.screens.profile.ProfileViewModelImpl
import com.kubsau.regrab.ui.screens.registration.WorkRegisterViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

@Deprecated("use separated modules for screens")
val viewModelModule = module {
    viewModelOf(::LatestWorksViewModelImpl)
    viewModelOf(::WorkRegisterViewModelImpl)
    viewModelOf(::UrlViewModelImpl)

    viewModelOf(::ProfileViewModelImpl)
}
