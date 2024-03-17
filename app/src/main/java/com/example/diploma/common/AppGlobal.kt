package com.example.diploma.common

import android.app.Application
import com.example.diploma.common.di.networkModule
import com.example.diploma.common.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppGlobal : Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin {
            androidLogger()
            androidContext(this@AppGlobal)
            modules(
                networkModule,
                viewModelModule
            )
        }

        NetworkConfig.attachContext(this)
    }

    companion object {
        private lateinit var instance: AppGlobal

        val Instance get() = instance
    }
}