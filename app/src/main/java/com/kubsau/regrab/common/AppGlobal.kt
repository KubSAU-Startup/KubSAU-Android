package com.kubsau.regrab.common

import android.app.Application
import androidx.preference.PreferenceManager
import com.kubsau.regrab.common.di.networkModule
import com.kubsau.regrab.common.di.viewModelModule
import com.kubsau.regrab.common.storage.AccountConfig
import com.kubsau.regrab.common.storage.NetworkConfig
import com.kubsau.regrab.ui.screens.auth.di.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppGlobal : Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoinInstance()

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        NetworkConfig.init(preferences)
        AccountConfig.init(preferences)
    }

    private fun startKoinInstance() {
        startKoin {
            androidLogger()
            androidContext(this@AppGlobal)
            modules(
                networkModule,
                viewModelModule,
                authModule
            )
        }
    }

    companion object {
        private lateinit var instance: AppGlobal

        @Deprecated("есть DI")
        val Instance get() = instance
    }
}
