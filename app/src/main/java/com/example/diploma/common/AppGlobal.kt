package com.example.diploma.common

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.diploma.common.di.networkModule
import com.example.diploma.common.di.viewModelModule
import com.example.diploma.common.storage.AccountConfig
import com.example.diploma.common.storage.NetworkConfig
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
                viewModelModule
            )
        }
    }

    companion object {
        private lateinit var instance: AppGlobal

        @Deprecated("есть DI")
        val Instance get() = instance
    }
}
