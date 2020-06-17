package com.scchao.wtrstkpractice.data.repository

import com.scchao.wtrstkpractice.api.WeatherAPI
import org.koin.dsl.module

val weatherDataRepoModule = module {
    factory { WeatherDataRepository(get()) }
}

class WeatherDataRepository ( private val weatherAPI: WeatherAPI ) {
    suspend fun queryWeather(key: String) = weatherAPI.queryCurrentWeather(key)
}