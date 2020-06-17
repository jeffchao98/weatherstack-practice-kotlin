package com.scchao.wtrstkpractice

import android.app.Application
import com.scchao.wtrstkpractice.api.networkModule
import com.scchao.wtrstkpractice.data.preferencesModule
import com.scchao.wtrstkpractice.data.repository.weatherDataRepoModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    networkModule,
                    preferencesModule,
                    weatherDataRepoModule
                )
            )
        }
    }
}
