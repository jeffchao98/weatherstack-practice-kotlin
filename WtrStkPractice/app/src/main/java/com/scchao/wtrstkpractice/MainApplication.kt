package com.scchao.wtrstkpractice

import android.app.Application
import com.scchao.wtrstkpractice.api.networkModule
import com.scchao.wtrstkpractice.data.room.dbSetupModule
import com.scchao.wtrstkpractice.data.repository.keywordRepoModule
import com.scchao.wtrstkpractice.data.repository.weatherDataRepoModule
import com.scchao.wtrstkpractice.ui.model.detailViewModel
import com.scchao.wtrstkpractice.ui.model.mainViewModel
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
                    dbSetupModule(),
                    keywordRepoModule,
                    weatherDataRepoModule,
                    mainViewModel,
                    detailViewModel
                )
            )
        }
    }
}
